package cn.flowboot.system.service;

import cn.flowboot.system.domain.dto.ConfigDto;
import cn.flowboot.system.domain.dto.RoleDto;
import cn.flowboot.system.domain.entity.SysConfig;
import cn.flowboot.system.domain.entity.SysRole;
import cn.flowboot.system.domain.query.ConfigQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 配置服务
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/27
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 查询列表
     * @param configQuery
     * @return
     */
    List<SysConfig> queryList(ConfigQuery configQuery);

    /**
     * 更新或新增
     * @param update 是否为更新
     * @param configDto 配置dto
     */
    void saveOrUpdate(boolean update, ConfigDto configDto);

    /**
     * 得到值
     * @param key
     * @return
     */
    Object getValue(String key);

}
