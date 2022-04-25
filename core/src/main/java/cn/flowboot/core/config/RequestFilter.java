package cn.flowboot.core.config;

import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <h1>解决跨域设置</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/12/01
 */

@WebFilter(filterName = "requestFilter", urlPatterns = {"/*"})
public class RequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }



    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 此处 setHeader、addHeader 方法都可用。但 addHeader时写多个会报错：“...,but only one is allowed”
        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.addHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        // 解决预请求（发送2次请求），此问题也可在 nginx 中作相似设置解决。
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Cache-Control,Pragma,Content-Type,Token, Content-Type");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        String method = request.getMethod();
        if (method.equalsIgnoreCase("OPTIONS")) {
            servletResponse.getOutputStream().write("Success".getBytes("utf-8"));
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

}
