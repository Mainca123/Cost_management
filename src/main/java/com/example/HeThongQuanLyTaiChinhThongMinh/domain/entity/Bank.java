package com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "bank")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private Long bankId;

    @Column(name = "bank_code")
    private Integer bankCode;

    @Column(name = "name_bank", length = 255)
    private String nameBank;

    @Column(name = "short_name", length = 100)
    private String shortName;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "status")
    private Byte status;

    @Column(name = "auth_endpoint")
    private String authEndpoint;

    @Column(name = "token_endpoint")
    private String tokenEndpoint;

    @Column(name = "redirect_url")
    private String redirectUrl;

    @Column(name = "account_info_endpoint")
    private String accountInfoEndpoint;

    @Column(name = "transfer_endpoint")
    private String transferEndpoint;

    @Column(name = "balance_endpoint")
    private String balanceEndpoint;

    // Relationships
    @OneToMany(mappedBy = "bank")
    private List<BankUser> bankUsers;
}
