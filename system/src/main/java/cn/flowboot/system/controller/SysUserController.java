package cn.flowboot.system.controller;

import cn.flowboot.common.croe.domain.AjaxResult;
import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.common.utils.SecurityUtils;
import cn.flowboot.system.domain.entity.SysUser;
import cn.flowboot.system.domain.vo.UserBaseVo;
import cn.flowboot.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/24
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/user/")
public class SysUserController {

    private final SysUserService sysUserService;

    @GetMapping("list")
    public AjaxResult list(){
        return AjaxResult.success(sysUserService.list());
    }


    @GetMapping("my")
    public AjaxResult info(Authentication authentication){

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        UserBaseVo.UserVo userVo = new UserBaseVo.UserVo();
        userVo.setUsername(loginUser.getUsername());
        userVo.setAvatar(loginUser.getAvatar());
        UserBaseVo userBaseVo = UserBaseVo.builder()
                .user(userVo)
                .permissions(AuthorityUtils.authorityListToSet(loginUser.getAuthorities()))
                .roles(loginUser.getRoles())
                .build();

        return AjaxResult.success(userBaseVo);
    }
}
