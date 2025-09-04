package com.example.HeThongQuanLyTaiChinhThongMinh.service;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.LoginRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.RegisterRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.LoginResponseDTO;

public interface AuthService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
    String register(RegisterRequestDTO registerRequestDTO);
}
