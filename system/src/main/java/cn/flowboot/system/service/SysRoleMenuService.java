package cn.flowboot.system.service;

import cn.flowboot.system.domain.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 角色菜单关联表操作
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/27
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 通过角色id删除
     * @param roleId
     */
    void removeByRoleId(Long roleId);
}
