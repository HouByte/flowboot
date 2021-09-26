package cn.flowboot.system.service;

import cn.flowboot.system.domain.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/**
 *
 */
public interface SysMenuService extends IService<SysMenu> {

    List<GrantedAuthority> queryPermissionsByUserId(Long userId);
}
