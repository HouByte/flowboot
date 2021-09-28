package cn.flowboot.system.service.impl;

import cn.flowboot.common.utils.AssertUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.flowboot.system.domain.entity.SysUserRole;
import cn.flowboot.system.service.SysUserRoleService;
import cn.flowboot.system.mapper.SysUserRoleMapper;
import org.assertj.core.util.Maps;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
implements SysUserRoleService{


    @Override
    public void removeByUserd(Long userId) {
        removeByMap(Maps.newHashMap("user_id",userId));
    }
}




