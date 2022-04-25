package cn.flowboot.system.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.stereotype.Component;

/**
 * <h1>密码无感知更新</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/08/30
 */
@Slf4j
@Component
public class ConsumerUserDetailsPasswordService implements UserDetailsPasswordService {

    /**
     * security 多密码模式下配置更新密码操作
     * @param userDetails
     * @param newPassword
     * @return
     */
    @Override
    public UserDetails updatePassword(UserDetails userDetails, String newPassword) {
        //TODO 多密码编码模式下，在用户登入时用户不是最新的密码方式将密码修改到最新操作
        return userDetails;
    }
}
