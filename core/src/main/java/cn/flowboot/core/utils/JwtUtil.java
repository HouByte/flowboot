package cn.flowboot.core.utils;

import cn.flowboot.common.croe.properties.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <h1>JWT 工具类</h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/09/03
 */
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    //签名访问令牌的密钥
    private Key getKey(){
        //生成签名密钥
        byte[] apiKeySecretBytes = Base64.getMimeDecoder().decode(jwtProperties.getAccessSecret());
        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    }
    //用于刷新令牌的密钥
    private Key getRefreshKey(){
        //生成签名密钥
        byte[] apiKeySecretBytes = Base64.getMimeDecoder().decode(jwtProperties.getRefreshSecret());
        return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    }
    /**
     * 创建token
     * @param map
     * @return
     */
    public String createJwtToken(Map<String,Object> map, Long expireTime, Key key){
        val now = System.currentTimeMillis();
        return Jwts.builder()
                .setId(jwtProperties.getId())
                .addClaims(map)
                .setSubject(jwtProperties.getId())
                .setIssuedAt(new Date(now))//签发时间
                .setExpiration(new Date(now +expireTime))//60s后过期
                .signWith(key,SignatureAlgorithm.HS512)//使用HS512加密
                .compact();
    }

    /**
     * 创建授权token
     * @param map
     * @return
     */
    public String createAccessToken(Map<String,Object> map){
        return createJwtToken(map,jwtProperties.getExpire(),getKey());
    }

    /**
     * 创建刷新token
     * @param map
     * @return
     */
    public String createRefreshToken(Map<String,Object> map){
        return createJwtToken(map,jwtProperties.getExpire(),getRefreshKey());
    }

    /**
     * 解析
     * @param token
     * @return
     */
    public Claims parser(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateAccessToken(String jwtToken) {
        return validateToken(jwtToken, getKey());
    }

    public boolean validateRefreshToken(String jwtToken) {
        return validateToken(jwtToken, getRefreshKey());
    }

    public boolean validateToken(String jwtToken, Key signKey) {
        try {
            Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String buildAccessTokenWithRefreshToken(String jwtToken) {
        return parseClaims(jwtToken, getRefreshKey())
                .map(claims -> Jwts.builder()
                        .setClaims(claims)
                        .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpire()))
                        .signWith(getKey(), signatureAlgorithm).compact())
                .orElseThrow(()->new AccessDeniedException("访问被拒绝"));
    }

    public Optional<Claims> parseClaims(String jwtToken) {
        return parseClaims(jwtToken,getKey());
    }
    public Optional<Claims> parseRefreshClaims(String jwtToken) {
        return parseClaims(jwtToken,getRefreshKey());
    }

    public Optional<Claims> parseClaims(String jwtToken, Key signKey) {
        try {
            val claims = Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(jwtToken).getBody();
            return Optional.of(claims);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public boolean validateWithoutExpiration(String jwtToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(jwtToken);
            return true;
        } catch (ExpiredJwtException | SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            if (e instanceof ExpiredJwtException) {
                return true;
            }
        }
        return false;
    }
}
