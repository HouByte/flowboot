package cn.flowboot.system.service.impl;

import cn.flowboot.common.constant.Constants;
import cn.flowboot.common.croe.domain.TreeSelect;
import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.common.exception.ParamsException;
import cn.flowboot.common.utils.AssertUtil;
import cn.flowboot.common.utils.CopyUtil;
import cn.flowboot.common.utils.SecurityUtils;
import cn.flowboot.system.domain.dto.MenuDto;
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
        List<SysMenu> menuTree = buildMenuTree(sysMenus);
        return toMenuNavList(menuTree);
    }

    @Override
    public List<Long> queryMenuIdsByRoleId(Long roleId) {
        return getBaseMapper().queryMenuIdsByRoleId(roleId);
    }

    @Override
    public List<TreeSelect> queryMenuTreeOptions() {
        List<SysMenu> sysMenus = querySelectList();
        if (sysMenus == null || sysMenus.size() == 0){
            return new ArrayList<>();
        }
        List<SysMenu> menuTree = buildMenuTree(sysMenus);

        return toTreeSelectList(menuTree);
    }

    @Override
    public List<SysMenu> queryMenuTrees() {
        List<SysMenu> list = list();
        return buildMenuTree(list);
    }

    @Override
    public void saveOrUpdate(boolean update, MenuDto menuDto) {
        SysMenu sysMenu = null;
        //校验
        verification(menuDto);

        String operator = SecurityUtils.getUsername();
        if (update){
            sysMenu = getById(menuDto.getMenuId());
            AssertUtil.isTrue(sysMenu == null,"更新数据不存在");
            sysMenu = updateData(sysMenu,menuDto);
            sysMenu.setUpdateBy(operator);
        } else {
            sysMenu = createData(menuDto);
            sysMenu.setCreateBy(operator);

        }

        AssertUtil.isTrue(!saveOrUpdate(sysMenu),"保存失败");

    }

    private void verification(MenuDto menuDto) {
        Long parentId = menuDto.getParentId();
        if (parentId == null){
            menuDto.setParentId(0L);
        } else {
            SysMenu sysMenu = getById(menuDto.getParentId());
            AssertUtil.isTrue(sysMenu == null,"父级菜单不存在");
        }
        //根据菜单分别分类校验
        switch (menuDto.getMenuType()){
            case Constants.MenuType.MENU:
                AssertUtil.isTrue(menuDto.getPath() == null,"路由地址不能为空");
                if (menuDto.getIsFrame()){
                    boolean isHttp = menuDto.getPath().startsWith("https://") || menuDto.getPath().startsWith("http://");
                    AssertUtil.isTrue(!isHttp,"外网地址需内链访问则以`http(s)://`开头");
                }
                break;
            case Constants.MenuType.CATALOG:
                AssertUtil.isTrue(menuDto.getPath() == null,"路由地址不能为空");
                if (!menuDto.getIsFrame()){
                    AssertUtil.isTrue(menuDto.getComponent() == null,"组件地址不存在");
                } else {
                    boolean isHttp = menuDto.getPath().startsWith("https://") || menuDto.getPath().startsWith("http://");
                    AssertUtil.isTrue(!isHttp,"外网地址需内链访问则以`http(s)://`开头");
                }
                break;
            case Constants.MenuType.FUNCTION:
                break;
            default:
                throw new ParamsException("类型错误");
        }
    }

    private SysMenu createData(MenuDto menuDto) {
        SysMenu temp = CopyUtil.copy(menuDto, SysMenu.class);
        temp.setCreateTime(new Date());
        temp.setUpdateTime(new Date());
        return temp;
    }

    private SysMenu updateData(SysMenu sysMenu, MenuDto menuDto) {
        SysMenu temp = CopyUtil.copy(menuDto, SysMenu.class);
        temp.setMenuId(sysMenu.getMenuId());
        temp.setCreateBy(sysMenu.getCreateBy());
        temp.setCreateTime(sysMenu.getCreateTime());
        temp.setUpdateTime(new Date());
        return temp;
    }

    private List<MenuNavVo> toMenuNavList(List<SysMenu> sysMenus) {
        if (sysMenus == null || sysMenus.size() == 0){
            return null;
        }
        return sysMenus.stream().map(this::toMenuNavVo).collect(Collectors.toList());
    }

    private List<TreeSelect> toTreeSelectList(List<SysMenu> menuNavVos) {
        if (menuNavVos == null || menuNavVos.size() == 0){
            return null;
        }
        return menuNavVos.stream().map(this::toTreeSelect).collect(Collectors.toList());
    }

    private TreeSelect toTreeSelect(SysMenu sysMenu){
        if (sysMenu == null){
            return null;
        }
        return TreeSelect.builder()
                .id(sysMenu.getMenuId())
                .label(sysMenu.getMenuName())
                .children(toTreeSelectList(sysMenu.getChildren()))
                .build();
    }

    private MenuNavVo toMenuNavVo(SysMenu sysMenu){
        if (sysMenu == null){
            return null;
        }
        MenuNavVo menuNavVo = CopyUtil.copy(sysMenu, MenuNavVo.class);
        menuNavVo.setTitle(sysMenu.getMenuName());
        menuNavVo.setName(sysMenu.getPath());
        menuNavVo.setId(sysMenu.getMenuId());
        menuNavVo.setChildren(toMenuNavList(sysMenu.getChildren()));
        return menuNavVo;
    }


    private List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        //List<MenuNavVo> menus = sysMenus.stream().map(sysMenu -> menuToVo(sysMenu)).collect(Collectors.toList());

        List<SysMenu> returnList = new ArrayList<SysMenu>();
        List<Long> tempList = new ArrayList<Long>();
        for (SysMenu dept : menus) {
            tempList.add(dept.getMenuId());
        }
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext();) {
            SysMenu menu = (SysMenu) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId())) {
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
    private void recursionFn(List<SysMenu> list, SysMenu menu) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, menu);
        menu.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<SysMenu>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = (SysMenu) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    private MenuNavVo menuToVo(SysMenu sysMenu){
        return MenuNavVo.builder()
                .id(sysMenu.getMenuId())
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




