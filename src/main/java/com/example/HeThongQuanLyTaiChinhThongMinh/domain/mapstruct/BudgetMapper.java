package com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.BudgetRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Budget;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BudgetMapper {
    BudgetRequestDTO toBudgetResponse(Budget budget);
}
