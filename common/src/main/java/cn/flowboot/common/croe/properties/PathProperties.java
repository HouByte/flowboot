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
@ConfigurationProperties(prefix = "flowboot.file")
public class PathProperties {

    private String path ;
}
