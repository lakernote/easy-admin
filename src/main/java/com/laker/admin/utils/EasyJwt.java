package com.laker.admin.utils;

import com.google.common.annotations.VisibleForTesting;
import com.laker.admin.framework.EasyAdminConstants;
import com.laker.admin.module.wx.miniapp.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class EasyJwt {
    private final JwtProperties jwtProperties;

    public EasyJwt(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @VisibleForTesting
    public static void main(String[] args) {
        SecretKey key = Jwts.SIG.HS256.key().build(); // 适用于 HS512
        System.out.println(java.util.Base64.getEncoder().encodeToString(key.getEncoded()));
    }

    public String generateToken(Long userId) {
        return Jwts.builder()
                .subject("easy-admin")
                .claim(EasyAdminConstants.USER_ID, userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration() * 1000))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()), Jwts.SIG.HS256)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
