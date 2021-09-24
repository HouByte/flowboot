package cn.flowboot.system.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/24
 */
@Data
@Builder
public class CaptchaVo {

    private String img;

    private String uuid;
}
