package cn.flowboot.system.service.impl;

import cn.flowboot.common.utils.AssertUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.flowboot.system.domain.entity.SysRoleMenu;
import cn.flowboot.system.service.SysRoleMenuService;
import cn.flowboot.system.mapper.SysRoleMenuMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu>
implements SysRoleMenuService{

    @Override
    public void removeByRoleId(Long roleId) {
        QueryWrapper<SysRoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        AssertUtil.isTrue(!remove(queryWrapper),"更新权限失败");
    }
}




