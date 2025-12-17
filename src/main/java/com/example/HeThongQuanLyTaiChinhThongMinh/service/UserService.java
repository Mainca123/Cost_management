package com.example.HeThongQuanLyTaiChinhThongMinh.service;


import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.UserResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.User;

public interface UserService {
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    UserResponseDTO getMe();
    void save(User user);
//    String setUser(SetUserRequestDTO setUserRequestDTO);
}
