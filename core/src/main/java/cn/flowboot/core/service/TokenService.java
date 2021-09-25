package cn.flowboot.core.service;

import cn.flowboot.common.croe.domain.user.LoginUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/25
 */
public interface TokenService {

    boolean checkIgnore(HttpServletRequest request);

    LoginUser getUser(HttpServletRequest request);

    String createToken(UserDetails user);
}
