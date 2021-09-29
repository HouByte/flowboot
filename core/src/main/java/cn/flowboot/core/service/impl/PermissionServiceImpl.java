package cn.flowboot.core.service.impl;

import cn.flowboot.common.constant.Constants;
import cn.flowboot.common.croe.domain.user.LoginUser;
import cn.flowboot.common.utils.SecurityUtils;
import cn.flowboot.core.service.PermissionService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 *  自定义权限实现
 *
 */
@Service("auth")
public class PermissionServiceImpl implements PermissionService {
    /**
     * 验证用户是否具备某权限
     *
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    @Override
    public boolean hasPermi(String permission) {
        if (StringUtils.isEmpty(permission)) {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (ObjectUtils.isEmpty(loginUser) || CollectionUtils.isEmpty(loginUser.getAuthorities())) {
            return false;
        }
        Set<String> permissions = AuthorityUtils.authorityListToSet(loginUser.getAuthorities());
        return hasPermissions(permissions, permission);
    }

    /**
     * 验证用户是否不具备某权限，与 hasPermi逻辑相反
     *
     * @param permission 权限字符串
     * @return 用户是否不具备某权限
     */
    @Override
    public boolean lacksPermi(String permission)
    {
        return hasPermi(permission) != true;
    }

    /**
     * 验证用户是否具有以下任意一个权限
     *
     * @param permissions 以 PERMISSION_NAMES_DELIMETER 为分隔符的权限列表
     * @return 用户是否具有以下任意一个权限
     */
    @Override
    public boolean hasAnyPermi(String permissions) {
        if (StringUtils.isEmpty(permissions)) {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (ObjectUtils.isEmpty(loginUser) || CollectionUtils.isEmpty(loginUser.getGrantedAuthorities())) {
            return false;
        }
        Set<String> authorities = AuthorityUtils.authorityListToSet(loginUser.getAuthorities());
        for (String permission : permissions.split(Constants.PERMISSION_DELIMETER)) {
            if (permission != null && hasPermissions(authorities, permission)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断用户是否拥有某个角色
     *
     * @param role 角色字符串
     * @return 用户是否具备某角色
     */
    @Override
    public boolean hasRole(String role) {
        if (StringUtils.isEmpty(role)) {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (ObjectUtils.isEmpty(loginUser) || CollectionUtils.isEmpty(loginUser.getRoles())) {
            return false;
        }
        for (String roleKey : loginUser.getRoles()) {

            if (Constants.SUPER_ADMIN.equals(roleKey) || roleKey.equals(StringUtils.trim(role))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证用户是否不具备某角色，与 isRole逻辑相反。
     *
     * @param role 角色名称
     * @return 用户是否不具备某角色
     */
    @Override
    public boolean lacksRole(String role)
    {
        return hasRole(role) != true;
    }

    /**
     * 验证用户是否具有以下任意一个角色
     *
     * @param roles 以 ROLE_NAMES_DELIMETER 为分隔符的角色列表
     * @return 用户是否具有以下任意一个角色
     */
    @Override
    public boolean hasAnyRoles(String roles) {
        if (StringUtils.isEmpty(roles)) {
            return false;
        }
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (ObjectUtils.isEmpty(loginUser) || CollectionUtils.isEmpty(loginUser.getRoles())) {
            return false;
        }
        for (String role : roles.split(Constants.ROLE_DELIMETER)) {
            if (hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否包含权限
     *
     * @param permissions 权限列表
     * @param permission 权限字符串
     * @return 用户是否具备某权限
     */
    private boolean hasPermissions(Set<String> permissions, String permission) {

        return permissions.contains(Constants.ALL_PERMISSION) || permissions.contains(StringUtils.trim(permission));
    }
}
