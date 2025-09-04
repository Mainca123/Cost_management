package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequestDTO {
    private String  type;
    private Double amount;
    private String note;
    private Long categoryId;
}

