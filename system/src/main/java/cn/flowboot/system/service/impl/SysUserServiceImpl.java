package cn.flowboot.system.service.impl;

import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.common.utils.AssertUtil;
import cn.flowboot.common.utils.CopyUtil;
import cn.flowboot.system.service.SysMenuService;
import cn.flowboot.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.flowboot.system.domain.entity.SysUser;
import cn.flowboot.system.service.SysUserService;
import cn.flowboot.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 *
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
implements SysUserService{

    private final SysMenuService sysMenuService;
    private final SysRoleService sysRoleService;

    @Override
    public SysUser getOneByUsername(String username) {
        AssertUtil.isTrue(username == null,"用户名不能为空");
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name",username);
        return getOne(queryWrapper);
    }

    @Override
    public LoginUser getLoginUserByUsername(String username) {
        SysUser sysUser = getOneByUsername(username);
        log.info("用户查询结果 {}",sysUser);
        LoginUser loginUser = CopyUtil.copy(sysUser, LoginUser.class);
        //获取角色和权限
        if (loginUser== null){
            return null;
        }

        List<GrantedAuthority> permissions = sysMenuService.queryPermissionsByUserId(sysUser.getUserId());
        Set<String> roles = sysRoleService.queryRolesByUserId(sysUser.getUserId());
        loginUser.setRoles(roles);
        loginUser.setGrantedAuthorities(permissions);
        log.info("返回数据 {}",loginUser);
        return loginUser;
    }
}




