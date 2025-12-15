package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ExpenseResponseDTO {
    private Long id;
    private String categoryName;
    private Double amount;
    private String note;
    private LocalDateTime spentAt;
    private LocalDateTime createdAt;
}
