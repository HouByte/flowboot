package cn.flowboot.system.controller;

import cn.flowboot.common.croe.controller.BaseController;
import cn.flowboot.common.croe.domain.AjaxResult;
import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.system.domain.vo.MenuNavVo;
import cn.flowboot.system.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("nav")
    public AjaxResult getNav(Authentication authentication){
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        List<MenuNavVo> menuNavVos = sysMenuService.queryCurrentUserNav(loginUser);
        return AjaxResult.success(menuNavVos);
    }

    @GetMapping("tree")
    public AjaxResult queryMenuTrees(){
        return success(sysMenuService.queryMenuTrees());
    }


    @GetMapping("roleMenuTreeselect/{id}")
    public AjaxResult roleMenuTreeselect(@PathVariable("id")Long id){
        return success(sysMenuService.queryMenuIdsByRoleId(id));
    }
}
