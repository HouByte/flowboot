package cn.flowboot.core.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * <h1>自定义登入过滤器</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/08/29
 */
@Slf4j
@RequiredArgsConstructor
public class RestAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;

    /**
     * {
     *     "username":"xxxx"
     *     "password":"xxxx"
     * }
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("过滤器处理 {}",request.getContextPath());
        UsernamePasswordAuthenticationToken authRequest;
        try {
            InputStream is = request.getInputStream();
            val jsonNode =objectMapper.readTree(is);
            String username = jsonNode.get("username").textValue();
            String password = jsonNode.get("password").textValue();
            authRequest = new  UsernamePasswordAuthenticationToken(username,password);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BadCredentialsException("认证失败");
        }
        setDetails(request,authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
