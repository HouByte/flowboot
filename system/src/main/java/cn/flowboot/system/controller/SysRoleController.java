package cn.flowboot.system.controller;

import cn.flowboot.common.croe.controller.BaseController;
import cn.flowboot.common.croe.domain.AjaxResult;
import cn.flowboot.common.croe.domain.BaseDelete;
import cn.flowboot.common.croe.domain.SelectOption;
import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.common.utils.AssertUtil;
import cn.flowboot.common.utils.CopyUtil;
import cn.flowboot.system.domain.dto.RoleDto;
import cn.flowboot.system.domain.dto.RoleQuery;
import cn.flowboot.system.domain.dto.UserDto;
import cn.flowboot.system.domain.dto.UserQuery;
import cn.flowboot.system.domain.entity.SysRole;
import cn.flowboot.system.domain.entity.SysUser;
import cn.flowboot.system.domain.vo.RoleOptions;
import cn.flowboot.system.domain.vo.RoleVo;
import cn.flowboot.system.domain.vo.UserBaseVo;
import cn.flowboot.system.domain.vo.UserVo;
import cn.flowboot.system.service.SysRoleService;
import cn.flowboot.system.service.SysUserService;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
@RequestMapping("/sys/role/")
public class SysRoleController extends BaseController {

    private final SysRoleService sysRoleService;

    @GetMapping("list")
    public AjaxResult list(RoleQuery roleQuery){
        PageHelper.startPage(roleQuery.getPage(),roleQuery.getLimit());
        List<SysRole> sysRoles = sysRoleService.queryList(roleQuery.getKeyword());

        return page(sysRoles, RoleVo.class);
    }

    @GetMapping("info/{id}")
    public AjaxResult info(@PathVariable("id")Long id){
        SysRole sysRole = sysRoleService.getById(id);
        AssertUtil.isTrue(sysRole == null,"用户不存在");
        return success(sysRole,RoleDto.class);
    }

    /**
     * 保存角色
     * id 必须为空防止前端修改请求成添加
     * @param roleDto
     * @return
     */
    @PostMapping("save")
    public AjaxResult save(@RequestBody RoleDto roleDto){
        AssertUtil.isTrue(roleDto.getRoleId() != null,"系统错误");
        sysRoleService.saveOrUpdate(false,roleDto);
        return success();
    }

    @PostMapping("update")
    public AjaxResult update(@RequestBody RoleDto roleDto){
        AssertUtil.isTrue(roleDto.getRoleId() == null,"更新数据不存在");
        sysRoleService.saveOrUpdate(true,roleDto);
        return success();
    }

    @PostMapping("delete")
    public AjaxResult delete(@RequestBody BaseDelete baseDelete){
        AssertUtil.isTrue(baseDelete == null || baseDelete.getIds() == null || baseDelete.getIds().size() == 0,"请选择删除数据");
        AssertUtil.isTrue(!sysRoleService.removeByIds(baseDelete.getIds()),"删除失败");
        return success();
    }

    @GetMapping("roleSelectOptions")
    public AjaxResult roleSelectOptions(){
        //查询可授权角色
        Set<SysRole> sysRoles = sysRoleService.queryRoleAll();
        List<RoleOptions> roleOptions = CopyUtil.copyList(Collections.singletonList(sysRoles), RoleOptions.class);
        return success(sysRoles);
    }
}
