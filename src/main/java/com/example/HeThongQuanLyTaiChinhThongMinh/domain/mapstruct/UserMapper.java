package com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.SetUserResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    SetUserResponseDTO toSetUserResponseDTO(User user);
}
