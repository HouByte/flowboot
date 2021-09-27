package cn.flowboot.system.controller;

import cn.flowboot.common.croe.controller.BaseController;
import cn.flowboot.common.croe.domain.AjaxResult;
import cn.flowboot.common.croe.domain.BaseDelete;
import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.common.utils.AssertUtil;
import cn.flowboot.common.utils.SecurityUtils;
import cn.flowboot.system.domain.dto.UserDto;
import cn.flowboot.system.domain.dto.UserQuery;
import cn.flowboot.system.domain.entity.SysUser;
import cn.flowboot.system.domain.vo.UserBaseVo;
import cn.flowboot.system.domain.vo.UserVo;
import cn.flowboot.system.service.SysUserService;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
public class SysUserController extends BaseController {

    private final SysUserService sysUserService;

    @GetMapping("list")
    public AjaxResult list(UserQuery userQuery){
        PageHelper.startPage(userQuery.getPage(),userQuery.getLimit());
        List<UserVo> userVos = sysUserService.queryList(userQuery.getKeyword());

        return page(userVos);
    }


    @GetMapping("my")
    public AjaxResult my(Authentication authentication){

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

    @GetMapping("info/{id}")
    public AjaxResult info(@PathVariable("id")Long id){
        UserDto userDto = sysUserService.queryOneById(id);
        return success(userDto);
    }

    @PostMapping("save")
    public AjaxResult save(@RequestBody UserDto userDto){
        sysUserService.saveOrUpdate(false,userDto);
        return success();
    }

    @PostMapping("update")
    public AjaxResult update(@RequestBody UserDto userDto){
        AssertUtil.isTrue(userDto.getUserId() == null,"更新数据不存在");
        sysUserService.saveOrUpdate(true,userDto);
        return success();
    }

    @PostMapping("delete")
    public AjaxResult delete(@RequestBody BaseDelete baseDelete){
        AssertUtil.isTrue(baseDelete == null || baseDelete.getIds() == null || baseDelete.getIds().size() == 0,"请选择删除数据");
        AssertUtil.isTrue(!sysUserService.removeByIds(baseDelete.getIds()),"删除失败");
        return success();
    }

}
