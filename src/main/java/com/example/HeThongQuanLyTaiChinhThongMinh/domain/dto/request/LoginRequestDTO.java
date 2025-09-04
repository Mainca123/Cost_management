package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "Not null email")
    @Email(message = "Not correct from email")
    private String email;

    @NotBlank(message = "Not null password")
    private String password;
}
