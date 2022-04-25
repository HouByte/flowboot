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

    /**
     * 检查是否排除
     * @param request
     * @return
     */
    boolean checkIgnore(HttpServletRequest request);

    /**
     * 得到登入用户信息
     * @param request
     * @return
     */
    LoginUser getUser(HttpServletRequest request);

    /**
     * 创建令牌
     *
     * @param user 用户信息
     * @return 令牌
     */
    String createToken(UserDetails user);
}
