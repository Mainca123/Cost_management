package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUserRequestDTO {
    private String fullName;
    private String gender;
    private String phone;
    @Email
    private String email;
}
