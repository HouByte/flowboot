package cn.flowboot.system.mapper;

import cn.flowboot.system.domain.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Entity cn.flowboot.system.domain.entity.SysMenu
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<String> queryPermissionsByUserId(Long userId);
}




