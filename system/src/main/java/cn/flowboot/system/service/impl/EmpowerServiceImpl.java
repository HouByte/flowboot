package cn.flowboot.system.service.impl;

import cn.flowboot.common.croe.service.EmpowerService;
import cn.flowboot.system.domain.entity.SysRole;
import cn.flowboot.system.domain.entity.SysUserRole;
import cn.flowboot.system.service.SysRoleService;
import cn.flowboot.system.service.SysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/11/17
 */
@RequiredArgsConstructor
@Service
public class EmpowerServiceImpl implements EmpowerService{

    private final SysRoleService sysRoleService;
    private final SysUserRoleService sysUserRoleService;
    /**
     * 通过权限字符授权
     *
     * @param uid
     * @param roleKey
     */
    @Override
    public Boolean empowerRole(Long uid, String roleKey) {
        SysRole sysRole = sysRoleService.queryRoleByRoleKey(roleKey);
        return sysUserRoleService.addUserRole(uid,sysRole.getRoleId());
    }
}
