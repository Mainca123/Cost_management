package com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BankUser")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankUser {

    @EmbeddedId
    private BankUserId id;

    @ManyToOne
    @MapsId("bankId")
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    private String accountNumber;
    private String accountName;
    private String accessToken;
    private String refreshToken;
    private String clientId;
    private String clientSecret;
}
