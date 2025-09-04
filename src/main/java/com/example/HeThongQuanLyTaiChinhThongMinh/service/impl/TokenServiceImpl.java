package com.example.HeThongQuanLyTaiChinhThongMinh.service.impl;


import com.example.HeThongQuanLyTaiChinhThongMinh.service.TokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    private static final String SECRET = "HETHONGQUANLYCHITIENTHONGMINHCUANHOM18TTCSN9015936167";
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    @Override
    public String generateAccessToken(String username, String passToken) {
        return Jwts.builder()
                .setSubject(username)
                .claim("tokenPass", passToken)
                .claim("type", "access")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(String username, String passToken) {
        return Jwts.builder()
                .setSubject(username)
                .claim("tokenPass", passToken)
                .claim("type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean verifyToken(String accessToken) {
        return verify(accessToken, "access");
    }

    @Override
    public boolean verifyTokenRefresh(String refreshToken) {
        return verify(refreshToken, "refresh");
    }

    private boolean verify(String token, String expectedType) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return expectedType.equals(claims.get("type"))
                    && claims.getExpiration().after(new Date());

        } catch (JwtException e) {
            log.debug("Invalid {} token: {}", expectedType, e.getMessage());
            return false;
        }
    }

    @Override
    public String extractUsername(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        } catch (JwtException e) {
            throw new RuntimeException("Invalid access token");
        }
    }
}

