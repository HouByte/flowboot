package cn.flowboot.core.service.impl;

import cn.flowboot.common.croe.domain.AjaxResult;
import cn.flowboot.common.croe.domain.user.LoginSuccess;
import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.core.service.AuthenticationService;
import cn.flowboot.core.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
    @Override
    public AjaxResult getResult(AbstractAuthenticationToken authenticationToken) {
        Collection<GrantedAuthority> authorities = authenticationToken.getAuthorities();
        System.out.println(authenticationToken);
        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();
        LoginSuccess.LoginUserVo loginUserVo = new LoginSuccess.LoginUserVo();
        loginUserVo.setUsername(loginUser.getUsername());
        loginUserVo.setAvatar(loginUser.getAvatar());
        LoginSuccess loginSuccess = LoginSuccess.builder()
                .token(tokenService.createToken(loginUser))
                .user(loginUserVo)
                .permissions(AuthorityUtils.authorityListToSet(authenticationToken.getAuthorities()))
                .roles(loginUser.getRoles())
                .build();
        return AjaxResult.success(loginSuccess);
    }
}
