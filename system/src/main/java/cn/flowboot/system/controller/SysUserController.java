package cn.flowboot.system.controller;

import cn.flowboot.common.croe.domain.AjaxResult;
import cn.flowboot.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RestController
@RequestMapping("/sys/user/")
public class SysUserController {

    private final SysUserService sysUserService;

    @GetMapping("list")
    public AjaxResult test(){
        return AjaxResult.success(sysUserService.list());
    }

}
