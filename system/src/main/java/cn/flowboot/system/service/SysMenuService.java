package cn.flowboot.system.service;

import cn.flowboot.common.croe.domain.TreeSelect;
import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.system.domain.dto.MenuDto;
import cn.flowboot.system.domain.entity.SysMenu;
import cn.flowboot.system.domain.vo.MenuNavVo;
import cn.flowboot.system.domain.vo.MenuTree;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 * 菜单服务
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/27
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查询权限
     * @param userId
     * @return
     */
    List<GrantedAuthority> queryPermissionsByUserId(Long userId);

    /**
     * 查询当前用户导航栏数据
     * @param loginUser
     * @return
     */
    List<MenuNavVo> queryCurrentUserNav(LoginUser loginUser);

    /**
     * 查询菜单id通过角色id
     * @param roleId
     * @return
     */
    List<Long> queryMenuIdsByRoleId(Long roleId);

    /**
     * 查询树选择器
     * @return
     */
    List<TreeSelect> queryMenuTreeOptions();

    /**
     * 查询树形列表
     * @return
     */
    List<SysMenu> queryMenuTrees();

    /**
     * 更新或新增
     * @param update 是否更新
     * @param menuDto
     */
    void saveOrUpdate(boolean update, MenuDto menuDto);

    /**
     * 删除菜单
     * @param id
     */
    void deleteMenu(Long id);

    /**
     * 更新状态
     * @param id
     * @param status
     */
    void updateStatus(Long id, Boolean status);
}
