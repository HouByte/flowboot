package cn.flowboot.system.service;

import cn.flowboot.system.domain.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    void removeByRoleId(Long roleId);
}
