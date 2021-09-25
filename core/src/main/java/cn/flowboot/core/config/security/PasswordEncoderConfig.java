package cn.flowboot.core.config.security;

import lombok.val;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>密码编码配置</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/01
 */
@Configuration
public class PasswordEncoderConfig {
    /**
     * 配置多密码编码
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        val idForDefault = "bcrypt";
        Map encoders = new HashMap();
        encoders.put(idForDefault,new BCryptPasswordEncoder());
        encoders.put("SHA-1",new MessageDigestPasswordEncoder("SHA-1"));
        return new DelegatingPasswordEncoder(idForDefault,encoders);
    }

}
