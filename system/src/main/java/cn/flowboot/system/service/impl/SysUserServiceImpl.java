package cn.flowboot.system.service.impl;

import cn.flowboot.common.croe.domain.SelectOption;
import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.common.utils.AssertUtil;
import cn.flowboot.common.utils.CopyUtil;
import cn.flowboot.common.utils.SecurityUtils;
import cn.flowboot.system.domain.dto.UserDto;
import cn.flowboot.system.domain.entity.SysRole;
import cn.flowboot.system.domain.entity.SysRoleMenu;
import cn.flowboot.system.domain.entity.SysUserRole;
import cn.flowboot.system.domain.vo.UserVo;
import cn.flowboot.system.service.SysMenuService;
import cn.flowboot.system.service.SysRoleService;
import cn.flowboot.system.service.SysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.flowboot.system.domain.entity.SysUser;
import cn.flowboot.system.service.SysUserService;
import cn.flowboot.system.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private final SysUserRoleService sysUserRoleService;
    private final PasswordEncoder passwordEncoder;

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
        Set<String> roles = sysRoleService.queryRoleKeysByUserId(sysUser.getUserId());
        loginUser.setRoles(roles);
        loginUser.setGrantedAuthorities(permissions);
        log.info("返回数据 {}",loginUser);
        return loginUser;
    }

    @Override
    public List<UserVo> queryList(String keyword) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag",0);
        if (StringUtils.isNotEmpty(keyword)){
            keyword = "%"+keyword+"%";
            queryWrapper.like("user_name",keyword).or().like("nick_name",keyword).or().like("email",keyword).or().like("phone",keyword);
        }
        return userToVo(list(queryWrapper));
    }

    /**
     * @param update  true 为更新
     * @param userDto
     */
    @Override
    public void saveOrUpdate(boolean update, UserDto userDto) {
        SysUser sysUser = null;
        String operator = SecurityUtils.getUsername();
        if (update){
            sysUser = getById(userDto.getUserId());
            AssertUtil.isTrue(sysUser == null,"更新数据不存在");
            updateData(sysUser,userDto);
            sysUser.setUpdateBy(operator);
        } else {
            sysUser = createData(userDto);
            sysUser.setCreateBy(operator);
        }

        AssertUtil.isTrue(!saveOrUpdate(sysUser),"保存失败");
        relationRole(update,sysUser.getUserId(),userDto.getRoleIds());
    }

    private void relationRole(boolean update, Long userId, Set<Long> roleIds) {
        if (update){
            sysUserRoleService.removeByUserd(userId);
        }
        if (roleIds == null || roleIds.size() == 0){
            return;
        }
        //新增不需要删除，更新删除后添加
        List<SysUserRole> sysUserRoles = roleIds.stream().map(roleId -> new SysUserRole(userId, roleId)).collect(Collectors.toList());
        AssertUtil.isTrue(!sysUserRoleService.saveBatch(sysUserRoles),"授权失败");
    }

    @Override
    public UserDto queryOneById(Long id) {
        SysUser sysUser = getById(id);
        AssertUtil.isTrue(sysUser == null,"获取用户不存在");
        sysUser.setPassword(null);
        //查询用户权限
        Set<Long> roleIds = sysRoleService.queryRoleIdsByUserId(id);
        //查询可授权角色
        Set<SysRole> sysRoles = sysRoleService.queryRoleAll();
        UserDto userDto = CopyUtil.copy(sysUser, UserDto.class);
        userDto.setRoleIds(roleIds);
        return userDto;
    }

    /**
     * 创建用户拷贝数据
     * @param userDto
     * @return
     */
    private SysUser createData(UserDto userDto) {
        SysUser sysUser = CopyUtil.copy(userDto, SysUser.class);
        sysUser.setDelFlag(false);
        sysUser.setUpdateTime(new Date());
        sysUser.setCreateTime(new Date());
        sysUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return sysUser;
    }

    /**
     * 更新数据拷贝
     * @param sysUser
     * @param userDto
     * @return
     */
    private void updateData(SysUser sysUser, UserDto userDto) {
        sysUser.setNickName(userDto.getNickName());
        sysUser.setPhone(userDto.getPhone());
        sysUser.setEmail(userDto.getEmail());
        sysUser.setDeptId(userDto.getDeptId());
        sysUser.setRemark(userDto.getRemark());
        sysUser.setSex(userDto.getSex());
        sysUser.setStatus(userDto.getStatus());
        sysUser.setUpdateTime(new Date());
    }

    private List<UserVo> userToVo(List<SysUser> list) {
        return CopyUtil.copyList(list,UserVo.class);
    }
}


