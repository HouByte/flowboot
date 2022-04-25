package cn.flowboot.system.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

/**
 * 用户信息编辑类
 * @TableName sys_user
 */
@Data
public class UserBaseDto implements Serializable {

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long userId;

    /**
     * 用户昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickName;


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


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
