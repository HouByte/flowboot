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
 *
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu>
implements SysRoleMenuService{

    @Override
    public void removeByRoleId(Long roleId) {
        System.out.println(roleId);
        boolean role_id = removeByMap(Maps.newHashMap("role_id", roleId));
        System.out.println(role_id);
    }
}




