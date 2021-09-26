package cn.flowboot.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.flowboot.system.domain.entity.SysRole;
import cn.flowboot.system.service.SysRoleService;
import cn.flowboot.system.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 *
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole>
implements SysRoleService{

    @Override
    public Set<String> queryRolesByUserId(Long userId) {
        return getBaseMapper().queryRolesByUserId(userId);
    }
}




