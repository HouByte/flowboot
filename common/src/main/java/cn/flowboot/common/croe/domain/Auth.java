package cn.flowboot.common.croe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Auth {
    private String accessToken;
    private String refreshToken;
}
