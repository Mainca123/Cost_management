package com.example.HeThongQuanLyTaiChinhThongMinh.controller;

import com.example.HeThongQuanLyTaiChinhThongMinh.base.RestApiV1;
import com.example.HeThongQuanLyTaiChinhThongMinh.base.VsResponseUtil;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestApiV1
@RequiredArgsConstructor
public class UserController {
//    private final UserService userService;
//
//    @GetMapping("/user/me")
//    public ResponseEntity<?> getMe(){
//        return VsResponseUtil.success(userService.getMe());
//    }
//
//    @PutMapping("/user/me")
//    public ResponseEntity<?> setMe(@RequestBody @Valid SetUserRequestDTO setUserRequestDTO){
//        return VsResponseUtil.success(userService.setUser(setUserRequestDTO));
//    }
}
