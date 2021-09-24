package cn.flowboot.common.croe.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <h1>jwt 配置</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/08/06
 */
@Data
@Component
@ConfigurationProperties(prefix = "flowboot.jwt")
public class JwtProperties {

    //密钥
    private String secret;
    //令牌过期时间(天）
    private int expire;
    //令牌缓存时间(天)
    private int cacheExpire;
}
