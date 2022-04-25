package cn.flowboot.system.controller;

import cn.flowboot.common.croe.controller.BaseController;
import cn.flowboot.common.croe.domain.AjaxResult;
import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.common.utils.AssertUtil;
import cn.flowboot.system.domain.dto.MenuDto;
import cn.flowboot.system.domain.dto.RoleDto;
import cn.flowboot.system.domain.vo.MenuNavVo;
import cn.flowboot.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/26
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/menu/")
public class SysMenuController extends BaseController {

    private final SysMenuService sysMenuService;

    /**
     * 导航数据
     * @param authentication
     * @return
     */
    @GetMapping("nav")
    public AjaxResult getNav(Authentication authentication){
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<MenuNavVo> menuNavVos = sysMenuService.queryCurrentUserNav(loginUser);
        return AjaxResult.success(menuNavVos);
    }

    /**
     * 树选项数据
     * @return
     */
    @GetMapping("treeOptions")
    public AjaxResult queryMenuTreeOptions(){
        return success(sysMenuService.queryMenuTreeOptions());
    }

    /**
     * 树表管理
     * @return
     */
    @GetMapping("tree")
    public AjaxResult queryMenuTrees(){
        return success(sysMenuService.queryMenuTrees());
    }

    /**
     * 角色选择菜单树数据
     * @param id
     * @return
     */
    @GetMapping("roleMenuTreeselect/{id}")
    public AjaxResult roleMenuTreeselect(@PathVariable("id")Long id){
        return success(sysMenuService.queryMenuIdsByRoleId(id));
    }

    /**
     * 保存角色
     * id 必须为空防止前端修改请求成添加
     * @param menuDto
     * @return
     */
    @PreAuthorize("@auth.hasPermi('sys:menu:save')")
    @PostMapping("save")
    public AjaxResult save(@Valid @RequestBody MenuDto menuDto){
        AssertUtil.isTrue(menuDto.getMenuId() != null,"系统错误");
        sysMenuService.saveOrUpdate(false,menuDto);
        return success();
    }

    /**
     * 更新
     * @param menuDto
     * @return
     */
    @PreAuthorize("@auth.hasPermi('sys:menu:update')")
    @PostMapping("update")
    public AjaxResult update(@Valid @RequestBody MenuDto menuDto){
        AssertUtil.isTrue(menuDto.getMenuId() == null,"更新数据不存在");
        sysMenuService.saveOrUpdate(true,menuDto);
        return success();
    }

    /**
     * 更新
     * @param id
     * @return
     */
    @PreAuthorize("@auth.hasPermi('sys:menu:delete')")
    @PostMapping("delete/{id}")
    public AjaxResult delete(@PathVariable Long id){

        sysMenuService.deleteMenu(id);
        return success();
    }

    /**
     * 更新状态
     * @param id
     * @param status
     * @return
     */
    @PreAuthorize("@auth.hasPermi('sys:menu:status')")
    @PostMapping("update/status/{id}")
    public AjaxResult updateStatus(@PathVariable("id")Long id,Boolean status){
        AssertUtil.isTrue(status == null,"系统异常");
        sysMenuService.updateStatus(id,status);
        return success();
    }
}
