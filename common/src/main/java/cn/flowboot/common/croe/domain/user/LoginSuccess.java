package cn.flowboot.common.croe.domain.user;

import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Set;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/05
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class LoginSuccess {
    private String token;
    private LoginUserVo user;
    private Set<String> roles;
    private Set<String> permissions;

    @NoArgsConstructor
    @Data
    public static class LoginUserVo{
        private String username;
        private String avatar;
    }
}
