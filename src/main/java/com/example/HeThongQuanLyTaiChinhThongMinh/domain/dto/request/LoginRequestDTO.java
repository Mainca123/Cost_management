package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    @Schema(
            description = "Email đăng nhập",
            example = "admin@gmail.com"
    )
    @NotBlank
    private String email;

    @Schema(
            description = "Mật khẩu đăng nhập",
            example = "Admin123456"
    )
    @NotBlank
    private String password;
}
