package com.example.HeThongQuanLyTaiChinhThongMinh.service;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.BudgetRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Budget;

public interface BudgetService {
    String create(BudgetRequestDTO budgetRequestDTO);
    BudgetRequestDTO getBudgetAmountLimit(Long categoryId);
    Budget findByCategoryId(Long categoryId);
    String setAmountLimit(Long category, Double amountLimit);
}
