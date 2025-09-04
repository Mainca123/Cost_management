package com.example.HeThongQuanLyTaiChinhThongMinh.controller;

import com.example.HeThongQuanLyTaiChinhThongMinh.base.RestApiV1;
import com.example.HeThongQuanLyTaiChinhThongMinh.base.VsResponseUtil;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.LoginRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.RegisterRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        return VsResponseUtil.success(authService.login(loginRequestDTO));
    }

    @PostMapping("auth/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequestDTO registerRequestDTO){
        return VsResponseUtil.success(authService.register(registerRequestDTO));
    }
}
