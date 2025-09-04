package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDTO {
    private Long transactionId;
    private String type;
    private Double amount;
    private String note;
    private LocalDate date;
}
