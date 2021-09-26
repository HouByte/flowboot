package cn.flowboot.system.service;

import cn.flowboot.system.domain.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 *
 */
public interface SysRoleService extends IService<SysRole> {
    Set<String> queryRolesByUserId(Long userId);
}
