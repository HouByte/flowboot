package cn.flowboot.system.controller;

import cn.flowboot.common.constant.Constants;
import cn.flowboot.common.croe.controller.BaseController;
import cn.flowboot.common.croe.domain.AjaxResult;
import cn.flowboot.common.croe.domain.BaseDelete;
import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.common.utils.AssertUtil;
import cn.flowboot.common.utils.CopyUtil;
import cn.flowboot.common.utils.RedisCache;
import cn.flowboot.common.utils.SecurityUtils;
import cn.flowboot.system.domain.dto.RegisterDto;
import cn.flowboot.system.domain.dto.UserBaseDto;
import cn.flowboot.system.domain.dto.UserDto;
import cn.flowboot.system.domain.entity.SysUser;
import cn.flowboot.system.domain.query.UserQuery;
import cn.flowboot.system.domain.vo.UserBaseVo;
import cn.flowboot.system.domain.vo.UserVo;
import cn.flowboot.system.service.SysUserService;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private final RedisCache redisCache;
    private static final Set<String> rootRole;

    static {
        rootRole = new HashSet<String>();
        rootRole.add("root");
    }
    /**
     * 用户列表
     * @param userQuery
     * @return
     */
    @PreAuthorize("@auth.hasPermi('sys:user:list')")
    @GetMapping("list")
    public AjaxResult list(UserQuery userQuery){
        PageHelper.startPage(userQuery.getPage(),userQuery.getLimit());
        List<UserVo> userVos = sysUserService.queryList(userQuery.getKeyword());

        return page(userVos);
    }


    /**
     * 当前登入用户信息
     * @param authentication
     * @return
     */
    @GetMapping("my")
    public AjaxResult my(Authentication authentication){

        Long userId = SecurityUtils.getUserId();
        String redisKey = Constants.LOGIN_ID_KEY + userId;
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        AssertUtil.isTrue(loginUser== null,"登入信息不存在");
        UserBaseVo.UserVo userVo = new UserBaseVo.UserVo();
        userVo.setUserId(loginUser.getUserId());
        userVo.setUsername(loginUser.getUsername());
        userVo.setAvatar(loginUser.getAvatar());
        userVo.setEmail(loginUser.getEmail());
        userVo.setPhone(loginUser.getPhone());
        userVo.setNickName(loginUser.getNickName());
        userVo.setSex(loginUser.getSex());
        UserBaseVo userBaseVo = UserBaseVo.builder()
                .user(userVo)
                .permissions(AuthorityUtils.authorityListToSet(loginUser.getAuthorities()))
                .roles(loginUser.getUserId() == 1 ? rootRole:loginUser.getRoles())
                .build();
        return AjaxResult.success(userBaseVo);
    }

    /**
     * 查询单个用户信息
     * @param id
     * @return
     */
    @PreAuthorize("@auth.hasPermi('sys:user:query')")
    @GetMapping("info/{id}")
    public AjaxResult info(@PathVariable("id")Long id){
        UserDto userDto = sysUserService.queryOneById(id);
        return success(userDto);
    }

    /**
     * 保存
     * @param userDto
     * @return
     */
    @PreAuthorize("@auth.hasPermi('sys:user:save')")
    @PostMapping("save")
    public AjaxResult save(@Valid @RequestBody UserDto userDto){
        AssertUtil.isTrue(userDto.getPassword() == null ,"密码不能为空");
        sysUserService.saveOrUpdate(false,userDto);
        return success();
    }

    /**
     * 更新
     * @param userDto
     * @return
     */
    @PostMapping("update")
    public AjaxResult update(@Valid @RequestBody UserDto userDto){
        AssertUtil.isTrue(userDto.getUserId() == null,"更新数据不存在");
        sysUserService.saveOrUpdate(true,userDto);
        return success();
    }

    /**
     * 基础信息更新
     * @param userDto
     * @return
     */
    @PostMapping("info/update")
    public AjaxResult userUpdate(@Valid @RequestBody UserBaseDto userDto){
        AssertUtil.isTrue(userDto.getUserId() == null,"更新数据不存在");
        SysUser sysUser = sysUserService.getById(userDto.getUserId());
        sysUser.setEmail(userDto.getEmail());
        sysUser.setUpdateBy(sysUser.getUserName());
        sysUser.setPhone(userDto.getPhone());
        sysUser.setSex(userDto.getSex());
        sysUser.setNickName(userDto.getNickName());
        sysUser.setAvatar(userDto.getAvatar());
        AssertUtil.isTrue(!sysUserService.updateById(sysUser),"更新失败");
        String redisKey = Constants.LOGIN_ID_KEY + sysUser.getUserId();
        LoginUser loginUser = sysUserService.getLoginUserByUsername(sysUser.getUserName());
        redisCache.setCacheObject(redisKey,loginUser);
        return success();
    }

    /**
     * 更新状态
     * @param id
     * @param status
     * @return
     */
    @PreAuthorize("@auth.hasPermi('sys:user:status')")
    @PostMapping("update/status/{id}")
    public AjaxResult updateStatus(@PathVariable("id")Long id,Boolean status){
        AssertUtil.isTrue(status == null,"系统异常");
        sysUserService.updateStatus(id,status);
        return success();
    }

    /**
     * 删除
     * @param baseDelete
     * @return
     */
    @PreAuthorize("@auth.hasPermi('sys:user:delete')")
    @PostMapping("delete")
    public AjaxResult delete(@Valid @RequestBody BaseDelete baseDelete){
        AssertUtil.isTrue(baseDelete == null || baseDelete.getIds() == null || baseDelete.getIds().size() == 0,"请选择删除数据");
        AssertUtil.isTrue(!sysUserService.removeByIds(baseDelete.getIds()),"删除失败");
        return success();
    }


    /**
     * 删除
     * @param registerDto
     * @return
     */
    @PostMapping("register")
    public AjaxResult register(@Valid @RequestBody RegisterDto registerDto){
        sysUserService.register(registerDto);
        return success("注册成功");
    }
}
