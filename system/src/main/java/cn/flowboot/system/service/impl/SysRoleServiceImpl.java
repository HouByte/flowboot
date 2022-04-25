package cn.flowboot.system.service.impl;

import cn.flowboot.common.utils.AssertUtil;
import cn.flowboot.common.utils.CopyUtil;
import cn.flowboot.common.utils.SecurityUtils;
import cn.flowboot.system.domain.dto.RoleDto;
import cn.flowboot.system.domain.dto.UserDto;
import cn.flowboot.system.domain.entity.SysRole;
import cn.flowboot.system.domain.entity.SysRoleMenu;
import cn.flowboot.system.domain.entity.SysUser;
import cn.flowboot.system.service.SysRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.flowboot.system.domain.entity.SysRole;
import cn.flowboot.system.service.SysRoleService;
import cn.flowboot.system.mapper.SysRoleMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/27
 */
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService{

    private final SysRoleMenuService sysRoleMenuService;

    /**
     * 获取角色标识 根据用户id
     * @param userId
     * @return
     */
    @Override
    public Set<String> queryRoleKeysByUserId(Long userId) {
        return getBaseMapper().queryRoleKeysByUserId(userId);
    }

    /**
     * 获取角色 根据用户id
     * @param userId
     * @return
     */
    @Override
    public Set<SysRole> queryRolesByUserId(Long userId) {
        return getBaseMapper().queryRolesByUserId(userId);
    }

    @Override
    public Set<Long> queryRoleIdsByUserId(Long userId) {
        return getBaseMapper().queryRoleIdsByUserId(userId);
    }

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public Set<SysRole> queryRoleAll() {
        return getBaseMapper().queryRoleAll();
    }

    /**
     * 管理列表
     * @param keyword
     * @return
     */
    @Override
    public List<SysRole> queryList(String keyword) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag",0);
        if (StringUtils.isNotEmpty(keyword)){
            keyword = "%"+keyword+"%";
            queryWrapper.like("role_name",keyword).or().like("role_key",keyword);
        }
        return list(queryWrapper);
    }

    /**
     * 更新或新增
     * @param update 是否更新
     * @param roleDto
     */
    @Override
    public void saveOrUpdate(boolean update, RoleDto roleDto) {
        SysRole sysRole = null;
        String operator = SecurityUtils.getUsername();
        if (update){
            sysRole = getById(roleDto.getRoleId());
            AssertUtil.isTrue(sysRole == null,"更新数据不存在");
            updateData(sysRole,roleDto);
            sysRole.setUpdateBy(operator);
        } else {
            sysRole = createData(roleDto);
            sysRole.setCreateBy(operator);

        }

        AssertUtil.isTrue(!saveOrUpdate(sysRole),"保存失败");
        relationMenu(update,sysRole.getRoleId(),roleDto.getMenuIds());
    }

    /**
     * 更新状态
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, Boolean status) {
        SysRole sysRole = getById(id);
        AssertUtil.isTrue(sysRole == null,"更新角色不存在");
        sysRole.setStatus(status);
        updateById(sysRole);
    }

    @Override
    public SysRole queryRoleByRoleKey(String roleKey) {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag",0).eq("role_key",roleKey);
        SysRole sysRole = getOne(queryWrapper);
        AssertUtil.isTrue(sysRole == null,"权限不存在");
        return sysRole;
    }


    /**
     * 角色和菜单关系更新
     * @param update
     * @param roleId
     * @param menuIds
     */
    private void relationMenu(Boolean update,Long roleId,List<Long> menuIds){
        if (update){
            //删除原有记录
            sysRoleMenuService.removeByRoleId(roleId);
        }
        if (menuIds == null || menuIds.size() == 0){
            return;
        }
        //新增不需要删除，更新删除后添加
        List<SysRoleMenu> sysRoleMenus = menuIds.stream().map(menuId -> new SysRoleMenu(roleId, menuId)).collect(Collectors.toList());
        AssertUtil.isTrue(!sysRoleMenuService.saveBatch(sysRoleMenus),"授权失败");
    }
    /**
     * 创建用户拷贝数据
     * @param roleDto
     * @return
     */
    private SysRole createData(RoleDto roleDto) {
        SysRole sysRole = CopyUtil.copy(roleDto, SysRole.class);
        sysRole.setDelFlag(false);
        sysRole.setDataScope("2");
        sysRole.setDeptCheckStrictly(true);
        sysRole.setMenuCheckStrictly(true);
        sysRole.setUpdateTime(new Date());
        sysRole.setCreateTime(new Date());
        return sysRole;
    }

    /**
     * 更新数据拷贝
     * @param sysRole
     * @param roleDto
     * @return
     */
    private void updateData(SysRole sysRole, RoleDto roleDto) {
        sysRole.setRoleName(roleDto.getRoleName());
        sysRole.setRoleKey(roleDto.getRoleKey());
        sysRole.setRoleSort(roleDto.getRoleSort());
        sysRole.setRemark(roleDto.getRemark());
        sysRole.setStatus(roleDto.getStatus());
        sysRole.setUpdateTime(new Date());
    }
}




