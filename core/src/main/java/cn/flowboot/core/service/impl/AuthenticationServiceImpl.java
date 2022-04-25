package cn.flowboot.core.service.impl;

import cn.flowboot.common.croe.domain.AjaxResult;
import cn.flowboot.common.croe.domain.user.LoginSuccess;
import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.core.service.AuthenticationService;
import cn.flowboot.core.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Sets;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/25
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {
    private final TokenService tokenService;
    private static final Set<String> rootRole;

    static {
        rootRole = new HashSet<String>();
        rootRole.add("root");
    }
    /**
     * 封装认证数据
     * @param authenticationToken
     * @return
     */
    @Override
    public AjaxResult getResult(AbstractAuthenticationToken authenticationToken) {
        Collection<GrantedAuthority> authorities = authenticationToken.getAuthorities();
        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();
        LoginSuccess.LoginUserVo loginUserVo = new LoginSuccess.LoginUserVo();
        loginUserVo.setUsername(loginUser.getUsername());
        loginUserVo.setAvatar(loginUser.getAvatar());
        System.out.println(loginUser);
        LoginSuccess loginSuccess = LoginSuccess.builder()
                .token(tokenService.createToken(loginUser))
                .user(loginUserVo)
                .permissions(AuthorityUtils.authorityListToSet(authenticationToken.getAuthorities()))
                .roles(loginUser.getUserId() ==1 ?  rootRole:loginUser.getRoles())
                .build();
        System.out.println(loginSuccess);
        return AjaxResult.success(loginSuccess);
    }
}
