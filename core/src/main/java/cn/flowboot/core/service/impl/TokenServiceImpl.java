package cn.flowboot.core.service.impl;

import cn.flowboot.common.constant.Constants;
import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.common.croe.properties.JwtProperties;
import cn.flowboot.common.utils.IdUtils;
import cn.flowboot.common.utils.RedisCache;
import cn.flowboot.core.service.TokenService;
import cn.flowboot.core.utils.JwtUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/25
 */
@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final RedisCache redisCache;

    @Override
    public boolean checkIgnore(HttpServletRequest request) {
        List<String> ignores = Arrays.asList(jwtProperties.getIgnoreFilter());
        return ignores.contains(request.getServletPath());
    }

    @Override
    public LoginUser getUser(HttpServletRequest request) {

        String jwt = request.getHeader(jwtProperties.getHeader());
        if (StrUtil.isNullOrUndefined(jwt)){
            return null;
        }
        jwt = jwt.replace(jwtProperties.getPrefix(),"");
        Optional<Claims> claims = null;
        try {
            if (!jwtUtil.validateWithoutExpiration(jwt)) {
                throw new JwtException("token已过期");
            }
            claims = jwtUtil.parseClaims(jwt);
            if (!claims.isPresent()) {
                throw new JwtException("token 异常");
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new JwtException(e.getMessage());
        }

        //通过jwt中的uuid查询用户信息
        String uuid = (String) claims.get().get(Constants.LOGIN_USER_KEY);
        return redisCache.getCacheObject(getTokenKey(uuid));
    }

    /**
     * 创建令牌
     *
     * @param user 用户信息
     * @return 令牌
     */
    @Override
    public String createToken(UserDetails user) {
        String token = IdUtils.simpleUUID();

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, token);
        //缓存存储
        redisCache.setCacheObject(getTokenKey(token),user);
        return jwtUtil.createAccessToken(claims);
    }


    private String getTokenKey(String uuid) {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }
}
