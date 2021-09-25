package cn.flowboot.system.service.impl;

import cn.flowboot.common.croe.service.AuthService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/24
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public User login(String username, String password) {
        return new User(username,password, AuthorityUtils.commaSeparatedStringToAuthorityList("sys:user:add"));
    }
}
