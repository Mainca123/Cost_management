package com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct;


import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.UserResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDTO toUserResponseDTO(User user);
}
