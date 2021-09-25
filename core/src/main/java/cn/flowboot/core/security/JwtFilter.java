package cn.flowboot.core.security;

import cn.flowboot.common.croe.properties.JwtProperties;
import cn.flowboot.core.utils.CollectionUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/05
 */
@RequiredArgsConstructor
@Component
@Order(-1)
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProperties jwtProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (checkJwtToken(request)){
            validateToken(request)
                    .filter(claims -> {
                        if (claims.get("authorities") != null){
                            setupSpringAuthentication(claims);
                            return true;
                        }
                        SecurityContextHolder.clearContext();

                        return false;
                    });
        }
        filterChain.doFilter(request,response);
    }

    private void setupSpringAuthentication(Claims claims) {
        val rawList = CollectionUtil.convertObjectToList(claims.get("authorities"));
        val authorities = rawList.stream()
                .map(String::valueOf)
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
        val authentication = new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private Optional<Claims> validateToken(HttpServletRequest request){
        String jwtToken = request.getHeader(jwtProperties.getHeader()).replace(jwtProperties.getPrefix(),"");
        try {
            return (Optional<Claims>) Optional.of(Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtProperties.getAccessSecret().getBytes())).build().parseClaimsJws(jwtToken).getBody());
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e){
            return Optional.empty();
        }
    }
    /**
     * 检查jwt token是否在HTTP报头中
     * @param request 请求
     * @return
     */
    private boolean checkJwtToken(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(jwtProperties.getHeader());
        return authenticationHeader != null && authenticationHeader.startsWith(jwtProperties.getPrefix());
    }


}
