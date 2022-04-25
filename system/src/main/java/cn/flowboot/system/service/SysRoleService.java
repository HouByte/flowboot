package cn.flowboot.system.service;

import cn.flowboot.system.domain.dto.RoleDto;
import cn.flowboot.system.domain.entity.SysRole;
import cn.flowboot.system.domain.vo.RoleVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 角色操作
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/27
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 获取角色标识 根据用户id
     * @param userId
     * @return
     */
    Set<String> queryRoleKeysByUserId(Long userId);

    /**
     * 获取角色 根据用户id
     * @param userId
     * @return
     */
    Set<SysRole> queryRolesByUserId(Long userId);

    /**
     * 获取角色id 根据用户id
     * @param userId
     * @return
     */
    Set<Long> queryRoleIdsByUserId(Long userId);

    /**
     * 查询所有角色
     * @return
     */
    Set<SysRole> queryRoleAll();

    /**
     * 管理列表
     * @param keyword
     * @return
     */
    List<SysRole> queryList(String keyword);

    /**
     * 更新或新增
     * @param update 是否更新
     * @param roleDto
     */
    void saveOrUpdate(boolean update, RoleDto roleDto);

    /**
     * 更新状态
     * @param id
     * @param status
     */
    void updateStatus(Long id, Boolean status);

    /***
     * 通过角色key获取角色
     * @param roleKey
     * @return
     */
    SysRole queryRoleByRoleKey(String roleKey);
}
