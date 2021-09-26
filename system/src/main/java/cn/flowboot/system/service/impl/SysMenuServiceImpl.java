package cn.flowboot.system.service.impl;

import cn.flowboot.common.utils.AssertUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.flowboot.system.domain.entity.SysMenu;
import cn.flowboot.system.service.SysMenuService;
import cn.flowboot.system.mapper.SysMenuMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
implements SysMenuService{

    @Override
    public List<GrantedAuthority> queryPermissionsByUserId(Long userId) {
        AssertUtil.isTrue(userId == null,"权限查询异常错误码：QIDERR");
        List<String> permissions = getBaseMapper().queryPermissionsByUserId(userId);
        return permissions.stream().map(permi -> new SimpleGrantedAuthority(permi)).collect(Collectors.toList());
    }
}




