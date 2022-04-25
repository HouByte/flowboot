package cn.flowboot.system.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/11/16
 */
@Data
public class RegisterDto {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
    @NotBlank(message = "非法请求")
    private String uuid;
    @NotBlank(message = "验证码不能为空")
    private String code;
}
