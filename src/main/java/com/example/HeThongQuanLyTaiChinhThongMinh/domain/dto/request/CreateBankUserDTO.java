package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.BankUserId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBankUserDTO {
    private BankUserId id;
    private String accountNumber;
    private String accountName;
    private String accessToken;
    private String refreshToken;
    private String clientId;
    private String clientSecret;
}
