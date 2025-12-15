package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BudgetResponseDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double limitAmount;
    private LocalDateTime createdAt;
}
