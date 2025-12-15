package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExpenseRequestDTO {
    @NotNull
    private long categoryId;
    @NotNull
    private Double amount;
    private String note;

    private LocalDateTime spentAt;

}
