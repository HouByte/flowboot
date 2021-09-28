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
 *
 */
public interface SysMenuService extends IService<SysMenu> {

    List<GrantedAuthority> queryPermissionsByUserId(Long userId);

    List<MenuNavVo> queryCurrentUserNav(LoginUser loginUser);

    List<Long> queryMenuIdsByRoleId(Long roleId);

    List<TreeSelect> queryMenuTreeOptions();

    List<SysMenu> queryMenuTrees();

    void saveOrUpdate(boolean update, MenuDto menuDto);
}
