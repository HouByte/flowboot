package cn.flowboot.system.domain.dto;

import cn.flowboot.common.croe.domain.BaseEntity;
import cn.flowboot.common.croe.domain.SelectOption;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 用户信息编辑类
 * @TableName sys_user
 */
@Data
public class UserDto implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户性别（0男 1女 2未知）
     */
    private String sex;

    /**
     * 头像地址
     */
    private String avatar;


    /**
     * 帐号状态（1正常 0停用）
     */
    private Boolean status;

    /**
     * 备注
     */
    private String remark;

    private Set<Long> roleIds;

    private List<SelectOption> roles;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
