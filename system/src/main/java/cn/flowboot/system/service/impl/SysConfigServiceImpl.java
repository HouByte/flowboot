package cn.flowboot.system.service.impl;

import cn.flowboot.common.utils.AssertUtil;
import cn.flowboot.common.utils.CopyUtil;
import cn.flowboot.common.utils.SecurityUtils;
import cn.flowboot.system.domain.dto.ConfigDto;
import cn.flowboot.system.domain.dto.RoleDto;
import cn.flowboot.system.domain.entity.SysRole;
import cn.flowboot.system.domain.query.ConfigQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.flowboot.system.domain.entity.SysConfig;
import cn.flowboot.system.service.SysConfigService;
import cn.flowboot.system.mapper.SysConfigMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig>
implements SysConfigService{

    @Override
    public List<SysConfig> queryList(ConfigQuery configQuery) {
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(configQuery.getConfigName())){
            queryWrapper.like("configName","%"+configQuery.getConfigName()+"%");
        }
        if (StringUtils.isNotEmpty(configQuery.getConfigKey())){
            queryWrapper.like("configKey","%"+configQuery.getConfigKey()+"%");
        }
        if (StringUtils.isNotEmpty(configQuery.getConfigValue())){
            queryWrapper.like("configValue","%"+configQuery.getConfigValue()+"%");
        }
        return list(queryWrapper);
    }

    @Override
    public void saveOrUpdate(boolean update, ConfigDto configDto) {
        SysConfig sysConfig = null;
        String operator = SecurityUtils.getUsername();
        if (update){
            sysConfig = getById(configDto.getConfigId());
            AssertUtil.isTrue(sysConfig == null,"更新数据不存在");
            //判断是否修改key 修改则判断键是否已存在
            AssertUtil.isTrue(!sysConfig.getConfigKey().equals(configDto.getConfigKey()) && getValue(configDto.getConfigKey()) != null,"配置key已存在");
            sysConfig = updateData(sysConfig,configDto);
            sysConfig.setUpdateBy(operator);
        } else {
            sysConfig = createData(configDto);
            sysConfig.setCreateBy(operator);

        }

        AssertUtil.isTrue(!saveOrUpdate(sysConfig),"保存失败");
    }

    @Override
    public Object getValue(String key) {
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("config_key",key);
        return getOne(queryWrapper).getConfigValue();
    }




    /**
     * 创建用户拷贝数据
     * @param configDto
     * @return
     */
    private SysConfig createData(ConfigDto configDto) {
        SysConfig sysConfig = CopyUtil.copy(configDto, SysConfig.class);
        System.out.println(sysConfig);
        sysConfig.setUpdateTime(new Date());
        sysConfig.setCreateTime(new Date());
        return sysConfig;
    }

    /**
     * 更新数据拷贝
     * @param sysConfig
     * @param configDto
     * @return
     */
    private SysConfig updateData(SysConfig sysConfig, ConfigDto configDto) {
        SysConfig config = CopyUtil.copy(configDto, SysConfig.class);
        config.setCreateTime(sysConfig.getCreateTime());
        config.setCreateBy(sysConfig.getCreateBy());
        config.setUpdateTime(new Date());
        return config;
    }
}




