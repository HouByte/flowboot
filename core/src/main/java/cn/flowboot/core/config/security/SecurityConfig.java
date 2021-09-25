package cn.flowboot.core.config.security;

import cn.flowboot.common.croe.domain.AjaxResult;
import cn.flowboot.core.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/24
 */
@RequiredArgsConstructor
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final ObjectMapper objectMapper;
    @Qualifier("consumerUserDetailsService")
    private final UserDetailsService consumerUserDetailsService;
    @Qualifier("consumerUserDetailsPasswordService")
    private final UserDetailsPasswordService consumerUserDetailsPasswordService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;




    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        return jwtAuthenticationFilter;
    }


    public static final String[] URL_WHITELIST = {
            "/webjars/**", "/favicon.ico",
            "/captcha", "/login", "/logout",
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.cors(Customizer.withDefaults()).csrf().disable() //关闭跨域
//                .headers().frameOptions().disable() //放行options请求
//                .and()
//                .formLogin(form->form.
//                        successHandler(getAuthenticationSuccessHandler())
//                        .failureHandler(getAuthenticationFailureHandler())
//                        .permitAll()
//                        )
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
////                .authorizeRequests(auth-> auth.anyRequest().authenticated())
//                .authorizeRequests(auth -> auth.antMatchers(URL_WHITELIST).permitAll().anyRequest().authenticated());

        http.authorizeRequests(req-> req
                .antMatchers(URL_WHITELIST).permitAll()
                .anyRequest()
                .authenticated())
                // 异常处理器
                .exceptionHandling(exc -> exc.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler))
                // 配置自定义的过滤器
                .addFilterBefore(jwtAuthenticationFilter(),RestAuthenticationFilter.class)
               // .addFilterBefore(captchaFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(restAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                //.httpBasic(Customizer.withDefaults())
                .headers(headers -> headers.frameOptions().disable()); //放行options请求
    }

    @Override
    public void configure(WebSecurity web) {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers(URL_WHITELIST);
    }


    /**
     * 创建自定义设置过滤器
     * @return
     * @throws Exception
     */
    private RestAuthenticationFilter restAuthenticationFilter() throws Exception {
        RestAuthenticationFilter filter = new RestAuthenticationFilter(objectMapper);
        //设置成功处理器
        filter.setAuthenticationSuccessHandler(getAuthenticationSuccessHandler());
        //设置失败处理器
        filter.setAuthenticationFailureHandler(getAuthenticationFailureHandler());
        //设置认证管理器
        filter.setAuthenticationManager(authenticationManager());

        //filter.setFilterProcessesUrl("/login");
        return filter;
    }

    private AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return (req, resp, exp) -> {
            //返回json格式字符串 val 代替 final ObjectMapper
            val objectMapper = new ObjectMapper();
            resp.setStatus(HttpStatus.UNAUTHORIZED.value());
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            resp.setCharacterEncoding("UTF-8");
            AjaxResult ajaxResult = AjaxResult.error(HttpStatus.UNAUTHORIZED.value(), "认证失败", exp.getMessage());
            resp.getWriter().print(objectMapper.writeValueAsString(ajaxResult)); //自定义返回结果
        };
    }

    private AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
        return (req, resp, auth) -> {
            //返回json格式字符串
            ObjectMapper objectMapper = new ObjectMapper();
            resp.setStatus(HttpStatus.OK.value());
            resp.setContentType(MediaType.APPLICATION_JSON_VALUE);
            resp.setCharacterEncoding("UTF-8");

            UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) auth;
            Auth build = Auth.builder()
                    .accessToken(jwtUtil.createAccessToken((UserDetails) authenticationToken.getPrincipal()))
                    .refreshToken(jwtUtil.createRefreshToken((UserDetails) authenticationToken.getPrincipal()))
                    .build();
            AjaxResult ajaxResult = AjaxResult.success(build);
            resp.getWriter().print(objectMapper.writeValueAsString(ajaxResult)); //自定义返回结果

        };
    }

    /**
     * 配置认证
     * @param auth
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    /**
     * 创建数据库认证bean
     */
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        val daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(consumerUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsPasswordService(consumerUserDetailsPasswordService);
        return daoAuthenticationProvider;
    }
}
