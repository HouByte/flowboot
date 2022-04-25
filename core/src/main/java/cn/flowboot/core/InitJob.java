package cn.flowboot.core;

import cn.flowboot.common.croe.properties.PathProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/12/27
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class InitJob {

    private final PathProperties pathProperties;

    @PostConstruct
    public void doConstruct() throws Exception {
        log.info("Flow Boot 项目启动");
        File root = new File(pathProperties.getPath());
        if (!root.exists()){
            root.mkdirs();
            log.info("创建根目录 {}",root);
        }
    }
}
