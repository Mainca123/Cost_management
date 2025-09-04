package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankUserInforResponseDTO {
    private String accountNumber;
    private String accountName;
    private String accessToken;
    private String refreshToken;
    private String clientId;
    private String clientSecret;
}
