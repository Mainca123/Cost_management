package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BudgetResponseDTO {
    private Double amountLimit;
    private Date startDate;
    private Date endDate;
}
