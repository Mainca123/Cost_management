package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    @NotBlank(message = "Not null full name")
    private String fullName;

    @NotBlank(message = "Not null email")
    @Email(message = "Not correct from email")
    private String email;

    @NotBlank(message = "Not null phone number")
    private String phoneNumber;
    @NotBlank(message = "Not null username")
    private String username;
    @NotBlank(message = "Not null password")
    private String password;

    private String gender;

}
