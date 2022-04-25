package cn.flowboot.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/12/01
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 页面跨域访问Controller过滤
     *
     * @return
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }
}

