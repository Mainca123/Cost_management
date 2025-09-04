package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetRequestDTO {
    private Double amountLimit;
    private Date startDate;
    private Date endDate;
    private Long categoryId;
}
