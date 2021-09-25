package cn.flowboot.system.service.impl;

import cn.flowboot.common.croe.domain.user.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername {}",username);
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList("sys:user:list");
        for (int i = 0; i < 50; i++) {
            authorityList.add(new SimpleGrantedAuthority("sys:user:add"+i));
        }
        //TODO 数据库查询用户和权限信息

        return LoginUser.builder()
                .userName("FlowBoot")
                .avatar("https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png")
                .password(passwordEncoder.encode("123456"))
                .delFlag(true)
                .status(true)
                .build();

    }
}
