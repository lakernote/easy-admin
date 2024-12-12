package com.laker.admin.utils;

import com.laker.admin.module.wx.miniapp.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EasyJwt {
    private final JwtProperties jwtProperties;

    public EasyJwt(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public String generateToken(String userId, String role) {
        return Jwts.builder()
                .subject(userId)
                .claim("role", role)
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration() * 1000))
                .signWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes()))
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
