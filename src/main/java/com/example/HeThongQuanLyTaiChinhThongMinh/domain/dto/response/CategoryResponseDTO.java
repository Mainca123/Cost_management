package com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryResponseDTO {
    private Long categoryId;
    private String name;
    private String description;
}
