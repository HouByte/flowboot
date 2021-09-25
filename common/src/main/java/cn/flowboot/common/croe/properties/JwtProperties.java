package cn.flowboot.common.croe.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

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

    private String id = "flowboot";
    private String header = "Authorization";
    private String prefix = "Bearer ";
    private Long accessTokenExpireTime = 600_000L;
    private Long refreshTokenExpireTime = 30 * 24 * 3600 * 1000L;
    //密钥
    private String accessSecret = "cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=";
    //refresh密钥
    private String refreshSecret = "cuAihCz53DZRaZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=";
    //令牌过期时间(天）
    private int expire;
    //令牌缓存时间(天)
    private int cacheExpire;
    private String[] ignoreFilter;
    private String[] ignoreAuth;
}
