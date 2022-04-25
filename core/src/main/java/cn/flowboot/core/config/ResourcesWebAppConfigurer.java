package cn.flowboot.core.config;

import cn.flowboot.common.croe.properties.PathProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * <h1>资源映射</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/11/08
 */
@RequiredArgsConstructor
@Configuration
public class ResourcesWebAppConfigurer implements WebMvcConfigurer {

    private final PathProperties pathProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("file:"+pathProperties.getPath());
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
