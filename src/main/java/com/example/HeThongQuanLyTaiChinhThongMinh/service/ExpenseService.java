package com.example.HeThongQuanLyTaiChinhThongMinh.service;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.ExpenseRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.ExpenseResponseDTO;

import java.util.List;

public interface ExpenseService {
    String createExpense(ExpenseRequestDTO expenseRequestDTO);
    List<ExpenseResponseDTO> getAll();
    double sumExpenseByBudget();
    List<Double> statistical(long year);
    ExpenseResponseDTO getExpense(Long id);
    String deleteExpense(Long id);
    String updateExpense(Long id, ExpenseRequestDTO expenseRequestDTO);
}
