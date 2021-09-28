package cn.flowboot.system.domain.vo;

import cn.flowboot.common.croe.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色信息表
 * @TableName sys_role
 */
@Data
public class RoleOptions extends BaseEntity implements Serializable {
    /**
     * 角色ID
     */
    @TableId(type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色权限字符串
     */
    private String roleKey;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
