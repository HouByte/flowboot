package cn.flowboot.system.service;

import cn.flowboot.system.domain.dto.ConfigDto;
import cn.flowboot.system.domain.dto.RoleDto;
import cn.flowboot.system.domain.entity.SysConfig;
import cn.flowboot.system.domain.entity.SysRole;
import cn.flowboot.system.domain.query.ConfigQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface SysConfigService extends IService<SysConfig> {

    List<SysConfig> queryList(ConfigQuery configQuery);

    void saveOrUpdate(boolean update, ConfigDto configDto);

    Object getValue(String key);

}
