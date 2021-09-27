package cn.flowboot.system.mapper;

import cn.flowboot.system.domain.entity.SysMenu;
import cn.flowboot.system.domain.vo.MenuTree;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Entity cn.flowboot.system.domain.entity.SysMenu
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<String> queryPermissionsByUserId(Long userId);

    List<Long> getMenuIdsByUserId(Long userId);

    List<Long> queryMenuIdsByRoleId(Long roleId);

    List<MenuTree> queryMenuTrees();

}




