package cn.flowboot.common.croe.service;

import org.springframework.security.core.userdetails.User;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/24
 */
public interface AuthService {

    public User login(String username, String password);
}
