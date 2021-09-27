package cn.flowboot.system.service.impl;

import cn.flowboot.common.constant.Constants;
import cn.flowboot.common.croe.domain.TreeSelect;
import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.common.utils.AssertUtil;
import cn.flowboot.system.domain.vo.MenuNavVo;
import cn.flowboot.system.domain.vo.MenuTree;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.flowboot.system.domain.entity.SysMenu;
import cn.flowboot.system.service.SysMenuService;
import cn.flowboot.system.mapper.SysMenuMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
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
        if (userId == 1){
            return AuthorityUtils.createAuthorityList("*:*:*");
        }
        List<String> permissions = getBaseMapper().queryPermissionsByUserId(userId);
        return permissions.stream().map(permi -> new SimpleGrantedAuthority(permi)).collect(Collectors.toList());
    }

    @Override
    public List<MenuNavVo> queryCurrentUserNav(LoginUser loginUser) {
        List<SysMenu> sysMenus = queryCurrentUserMenu(loginUser);
        return buildMenuTree(sysMenus);
    }

    @Override
    public List<Long> queryMenuIdsByRoleId(Long roleId) {
        return getBaseMapper().queryMenuIdsByRoleId(roleId);
    }

    @Override
    public List<TreeSelect> queryMenuTrees() {
        List<SysMenu> sysMenus = querySelectList();
        if (sysMenus == null || sysMenus.size() == 0){
            return new ArrayList<>();
        }
        List<MenuNavVo> menuNavVos = buildMenuTree(sysMenus);

        return toTreeSelectList(menuNavVos);
    }

    private List<TreeSelect> toTreeSelectList(List<MenuNavVo> menuNavVos) {
        if (menuNavVos == null || menuNavVos.size() == 0){
            return null;
        }
        return menuNavVos.stream().map(this::toTreeSelect).collect(Collectors.toList());
    }

    private TreeSelect toTreeSelect(MenuNavVo menuNavVo){
        if (menuNavVo == null){
            return null;
        }
        return TreeSelect.builder()
                .id(menuNavVo.getId())
                .label(menuNavVo.getTitle())
                .children(toTreeSelectList(menuNavVo.getChildren()))
                .build();
    }


    private List<MenuNavVo> buildMenuTree(List<SysMenu> sysMenus) {
        List<MenuNavVo> menus = sysMenus.stream().map(sysMenu -> menuToVo(sysMenu)).collect(Collectors.toList());

        List<MenuNavVo> returnList = new ArrayList<MenuNavVo>();
        List<Long> tempList = new ArrayList<Long>();
        for (MenuNavVo dept : menus) {
            tempList.add(dept.getId());
        }
        for (Iterator<MenuNavVo> iterator = menus.iterator(); iterator.hasNext();) {
            MenuNavVo menu = (MenuNavVo) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getPid())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;

    }

    /**
     * 递归建树
     * @param list
     * @param menu
     */
    private void recursionFn(List<MenuNavVo> list, MenuNavVo menu) {
        // 得到子节点列表
        List<MenuNavVo> childList = getChildList(list, menu);
        menu.setChildren(childList);
        for (MenuNavVo tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<MenuNavVo> getChildList(List<MenuNavVo> list, MenuNavVo t) {
        List<MenuNavVo> tlist = new ArrayList<MenuNavVo>();
        Iterator<MenuNavVo> it = list.iterator();
        while (it.hasNext()) {
            MenuNavVo n = (MenuNavVo) it.next();
            if (n.getPid().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<MenuNavVo> list, MenuNavVo t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    private MenuNavVo menuToVo(SysMenu sysMenu){
        return MenuNavVo.builder()
                .id(sysMenu.getMenuId())
                .pid(sysMenu.getParentId())
                .title(sysMenu.getMenuName())
                .isCache(sysMenu.getIsCache())
                .isFrame(sysMenu.getIsFrame())
                .perms(sysMenu.getPerms())
                .path(sysMenu.getPath())
                .name(sysMenu.getPath())
                .icon(sysMenu.getIcon())
                .component(sysMenu.getComponent())
                .build();
    }


    public List<SysMenu> querySelectList() {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1).eq("visible",1);
        return list(queryWrapper);
    }

    public List<SysMenu> queryCurrentUserMenu(LoginUser loginUser) {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status",1).eq("visible",1);
        if (loginUser.isAdmin()){
            return list(queryWrapper);
        }
        //查询用户拥有的权限
        List<Long> menuIds = getBaseMapper().getMenuIdsByUserId(loginUser.getUserId());
        queryWrapper.in("menu_id", menuIds);
        return list(queryWrapper);
    }
}




