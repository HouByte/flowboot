package cn.flowboot.common.croe.service;

import cn.flowboot.common.croe.domain.user.User;

import java.util.Map;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/11/13
 */
public interface UserService {

    /**
     * 获取用户map
     * @return
     */
    Map<Long, User> getUserMapByAvailable();

    /**
     * 通过id查询用户
     * @param id
     * @return
     */
    User getById(Long id);
}
