package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserResponseDTO {
    private String username;
    private String fullName;
    private String email;
}
