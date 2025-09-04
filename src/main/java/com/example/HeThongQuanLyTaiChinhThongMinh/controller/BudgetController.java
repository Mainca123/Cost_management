package com.example.HeThongQuanLyTaiChinhThongMinh.controller;

import com.example.HeThongQuanLyTaiChinhThongMinh.base.RestApiV1;
import com.example.HeThongQuanLyTaiChinhThongMinh.base.VsResponseUtil;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.BudgetRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.BudgetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestApiV1
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping("/user/budget")
    public ResponseEntity<?> createBudget(@RequestBody BudgetRequestDTO budgetRequestDTO){
        return VsResponseUtil.success(budgetService.create(budgetRequestDTO));
    }

    @GetMapping("/user/budget")
    public ResponseEntity<?> getAmountLimit(@RequestParam Long categoryId){
        return VsResponseUtil.success(budgetService.getBudgetAmountLimit(categoryId));
    }

    @PutMapping("/user/budget")
    public ResponseEntity<?> setBudget(@RequestParam Long categoryId, @RequestParam Double amount){
        return VsResponseUtil.success(budgetService.setAmountLimit(categoryId, amount));
    }
}
