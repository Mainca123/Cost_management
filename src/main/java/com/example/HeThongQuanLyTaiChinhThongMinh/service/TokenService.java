package com.example.HeThongQuanLyTaiChinhThongMinh.service;

public interface TokenService {
    String generateAccessToken(String username, String passToken);
    String generateRefreshToken(String username, String passToken);
    boolean verifyToken(String accessToken);
    boolean verifyTokenRefresh(String refreshToken);
    String extractUsername(String token);
}
