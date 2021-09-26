package cn.flowboot.system.service.impl;

import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <h1>加载用户信息</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/08/30
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ConsumerUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername {}",username);
        //数据库查询用户和权限信息
        LoginUser loginUser = sysUserService.getLoginUserByUsername(username);
        if (loginUser == null){
            throw new BadCredentialsException("用户名或密码不存在");
        }
        return loginUser;
    }
}
