package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListBankResponseDTO {
    private Long bankId;
    private String shortName;
}
