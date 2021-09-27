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
    //密钥
    private String accessSecret = "cuAihCz53DZRjZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=";
    //refresh密钥
    private String refreshSecret = "cuAihCz53DZRaZwbsGcZJ2Ai6At+T142uphtJMsk7iQ=";
    //令牌过期时间(毫秒） 默认一天
    private Long expire = 1000L * 60 * 60 * 24;
    //令牌缓存时间(毫秒)
    private Long cacheExpire = 1000L * 60 * 60 * 24;
    private String[] ignoreFilter;
    private String[] ignoreAuth;
}
