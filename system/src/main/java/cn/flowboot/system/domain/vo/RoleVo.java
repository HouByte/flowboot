package cn.flowboot.system.domain.vo;

import cn.flowboot.common.croe.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色信息表
 * @TableName sys_role
 */
@Data
public class RoleVo extends BaseEntity implements Serializable {
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

    /**
     * 角色状态（1正常 0停用）
     */
    private Boolean status;

    /**
     * 显示顺序
     */
    private Integer roleSort;

    /**
     * 创建者
     */
    private String createBy;


    /**
     * 更新者
     */
    private String updateBy;


    /**
     * 备注
     */
    private String remark;


    /**
     * 菜单树选择项是否关联显示
     */
    private Boolean menuCheckStrictly;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
