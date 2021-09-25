package cn.flowboot.core.config.security;



import cn.flowboot.common.croe.properties.JwtProperties;
import cn.flowboot.core.utils.JwtUtil;
import cn.hutool.core.util.StrUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/24
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtProperties jwtProperties;

    public static final String[] URL_WHITELIST = {
            "/webjars/**", "/favicon.ico",
            "/captcha", "/login", "/logout",
    };

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if ("/login".equals(request.getServletPath())){
            chain.doFilter(request, response);
            return;
        }

        String jwt = request.getHeader(jwtProperties.getHeader());
        if (StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request, response);
            return;
        }
        jwt = jwt.replace(jwtProperties.getPrefix(),"");
        try {
            Optional<Claims> claims = jwtUtil.parseClaims(jwt);
            System.out.println(claims+","+claims.isPresent());
            if (!claims.isPresent()) {
                throw new JwtException("token 异常");
            }
            if (!jwtUtil.validateWithoutExpiration(jwt)) {
                throw new JwtException("token已过期");
            }

        } catch (Exception e){
            e.printStackTrace();
            throw new JwtException("token 异常");
        }
        String username = "test";//claims.get().getSubject();
        // 获取用户的权限等信息


//        SysUser sysUser = sysUserService.getByUsername(username);
        UsernamePasswordAuthenticationToken token
                = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.createAuthorityList("sys:user:list"));

        SecurityContextHolder.getContext().setAuthentication(token);

        chain.doFilter(request, response);
    }
}
