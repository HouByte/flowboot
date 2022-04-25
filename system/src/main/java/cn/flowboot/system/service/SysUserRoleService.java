package cn.flowboot.system.service;

import cn.flowboot.system.domain.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *角色和用户关系操作
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/27
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 根据用户删除角色和用户关系
     * @param userId
     */
    void removeByUserd(Long userId);

    /**
     * 添加角色
     * @param userId
     * @param roleId
     * @return
     */
    Boolean addUserRole(Long userId, Long roleId);
}
