package cn.flowboot.system.service;

import cn.flowboot.system.domain.dto.RoleDto;
import cn.flowboot.system.domain.entity.SysRole;
import cn.flowboot.system.domain.vo.RoleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 *
 */
public interface SysRoleService extends IService<SysRole> {
    Set<String> queryRoleKeysByUserId(Long userId);

    Set<SysRole> queryRolesByUserId(Long userId);

    Set<Long> queryRoleIdsByUserId(Long userId);

    Set<SysRole> queryRoleAll();

    List<SysRole> queryList(String keyword);

    void saveOrUpdate(boolean update, RoleDto roleDto);
}
