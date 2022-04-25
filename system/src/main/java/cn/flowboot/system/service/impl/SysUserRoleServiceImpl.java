package cn.flowboot.system.service.impl;

import cn.flowboot.common.utils.AssertUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.flowboot.system.domain.entity.SysUserRole;
import cn.flowboot.system.service.SysUserRoleService;
import cn.flowboot.system.mapper.SysUserRoleMapper;
import org.assertj.core.util.Maps;
import org.springframework.stereotype.Service;

/**
 * 角色和用户关系操作
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/27
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
implements SysUserRoleService{


    /**
     * 根据用户删除角色和用户关系
     * @param userId
     */
    @Override
    public void removeByUserd(Long userId) {
        removeByMap(Maps.newHashMap("user_id",userId));
    }

    /**
     * 添加角色
     *
     * @param userId
     * @param roleId
     * @return
     */
    @Override
    public Boolean addUserRole(Long userId, Long roleId) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userId);
        sysUserRole.setRoleId(roleId);
        return save(sysUserRole);
    }
}




