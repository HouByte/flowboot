package cn.flowboot.core.config.security;


import cn.flowboot.common.croe.domain.AjaxResult;
import cn.flowboot.common.croe.service.AuthService;
import cn.flowboot.core.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authorize")
public class AuthorizeResource {

    private final AuthService authService;

    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public AjaxResult login(){

        return AjaxResult.error("未登入");
    }

    @PostMapping("/token/refresh")
    public Auth refreshToken(@RequestHeader(name = "Authorization") String authorization, @RequestParam String refreshToken) {
        val PREFIX = "Bearer ";
        val accessToken = authorization.replace(PREFIX, "");
        if (jwtUtil.validateRefreshToken(refreshToken) && jwtUtil.validateWithoutExpiration(accessToken)) {
            return new Auth(jwtUtil.buildAccessTokenWithRefreshToken(refreshToken), refreshToken);
        }
        throw new AccessDeniedException("Bad Credentials");
    }
}
