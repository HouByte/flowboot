package cn.flowboot.system.controller;

import cn.flowboot.common.croe.controller.BaseController;
import cn.flowboot.common.croe.domain.AjaxResult;
import cn.flowboot.common.croe.domain.BaseDelete;
import cn.flowboot.common.utils.AssertUtil;
import cn.flowboot.system.domain.dto.ConfigDto;
import cn.flowboot.system.domain.dto.RoleDto;
import cn.flowboot.system.domain.entity.SysConfig;
import cn.flowboot.system.domain.query.ConfigQuery;
import cn.flowboot.system.domain.query.RoleQuery;
import cn.flowboot.system.domain.entity.SysRole;
import cn.flowboot.system.domain.vo.RoleVo;
import cn.flowboot.system.service.SysConfigService;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@RequestMapping("/sys/config/")
public class SysConfigController extends BaseController {

    private final SysConfigService sysConfigService;

    /**
     * 配置列表
     */
    @PreAuthorize("@auth.hasPermi('sys:config:save')")
    @GetMapping("list")
    public AjaxResult list(ConfigQuery configQuery){
        PageHelper.startPage(configQuery.getPage(),configQuery.getLimit());
        List<SysConfig> sysConfigs = sysConfigService.queryList(configQuery);

        return page(sysConfigs);
    }

    /**
     * 保存角色
     * id 必须为空防止前端修改请求成添加
     * @param configDto
     * @return
     */
    @PreAuthorize("@auth.hasPermi('sys:config:save')")
    @PostMapping("save")
    public AjaxResult save(@Valid @RequestBody ConfigDto configDto){
        AssertUtil.isTrue(configDto.getConfigId() != null,"系统错误");
        sysConfigService.saveOrUpdate(false,configDto);
        return success();
    }

    /**
     * 更新配置
     * @param configDto
     * @return
     */
    @PreAuthorize("@auth.hasPermi('sys:config:update')")
    @PostMapping("update")
    public AjaxResult update(@Valid @RequestBody ConfigDto configDto){
        AssertUtil.isTrue(configDto.getConfigId() == null,"更新数据不存在");
        sysConfigService.saveOrUpdate(true,configDto);
        return success();
    }

    /**
     * 删除配置
     * @param baseDelete
     * @return
     */
    @PreAuthorize("@auth.hasPermi('sys:config:delete')")
    @PostMapping("delete")
    public AjaxResult delete(@Valid @RequestBody BaseDelete baseDelete){
        AssertUtil.isTrue(baseDelete == null || baseDelete.getIds() == null || baseDelete.getIds().size() == 0,"请选择删除数据");
        AssertUtil.isTrue(!sysConfigService.removeByIds(baseDelete.getIds()),"删除失败");
        return success();
    }

    /**
     * 查询配置
     * @param key
     * @return
     */
    @PreAuthorize("@auth.hasPermi('sys:config:query')")
    @GetMapping("get/{key}")
    public AjaxResult getValue(@PathVariable("key")String key){
        Object value = sysConfigService.getValue(key);
        AssertUtil.isTrue(value == null,key+" 不存在");
        return success(value);
    }


}
