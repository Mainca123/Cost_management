package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class LoginResponseDTO {
    private String token;
}
