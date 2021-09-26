package cn.flowboot.system.domain.vo;

import cn.flowboot.common.croe.domain.user.LoginSuccess;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/26
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserBaseVo {

    private UserVo user;
    private Set<String> roles;
    private Set<String> permissions;

    @NoArgsConstructor
    @Data
    public static class UserVo{
        private String username;
        private String avatar;
    }
}
