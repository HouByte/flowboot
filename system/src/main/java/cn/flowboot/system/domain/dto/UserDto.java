package cn.flowboot.system.domain.dto;

import cn.flowboot.common.croe.domain.BaseEntity;
import cn.flowboot.common.croe.domain.SelectOption;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
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
    @NotBlank(message = "用户名不能为空")
    @Length(min = 4,max = 16,message = "用户名长度需要在4~16之间")
    private String userName;

    /**
     * 用户昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickName;

//    @NotBlank(message = "密码不能为空")
//    @Length(min = 4,max = 16,message = "密码设置长度需要在4~16之间")
    private String password;

    /**
     * 用户邮箱
     */
    @Pattern(regexp = "^\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}$")
    private String email;

    /**
     * 手机号码
     */
    @Pattern(regexp = "^0?(13|14|15|18|17)[0-9]{9}$")
    private String phone;

    /**
     * 用户性别（0男 1女 2未知）
     */
    @Range(min = 0,max = 2,message = "性别不正确")
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
