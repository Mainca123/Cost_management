package com.example.HeThongQuanLyTaiChinhThongMinh.service;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.BudgetRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.BudgetResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Budget;

import java.util.List;

public interface BudgetService {
    String create(BudgetRequestDTO budgetRequestDTO);
    String setAmountLimit(Double amountLimit);
    List<BudgetResponseDTO> getAll();
    Budget findBudgetNow();
    Double sumExpenseRemaining();
    String setBudget(Long id, Double limitAmount);
    String deleteBudget(Long id);
}
