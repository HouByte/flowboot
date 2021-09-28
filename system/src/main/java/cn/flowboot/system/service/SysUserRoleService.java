package cn.flowboot.system.service;

import cn.flowboot.system.domain.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    void removeByUserd(Long userId);
}
