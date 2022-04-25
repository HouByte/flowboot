package cn.flowboot.system.service.impl;

import cn.flowboot.common.utils.AssertUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.flowboot.system.domain.entity.SysRoleMenu;
import cn.flowboot.system.service.SysRoleMenuService;
import cn.flowboot.system.mapper.SysRoleMenuMapper;
import org.assertj.core.util.Maps;
import org.springframework.stereotype.Service;

/**
 * 角色菜单关联表操作
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/27
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu>
implements SysRoleMenuService{

    /**
     * 通过角色id删除
     * @param roleId
     */
    @Override
    public void removeByRoleId(Long roleId) {
        System.out.println(roleId);
        boolean role_id = removeByMap(Maps.newHashMap("role_id", roleId));
        System.out.println(role_id);
    }
}




