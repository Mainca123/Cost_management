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

    // CREATE
    @PostMapping("/user/budget")
    public ResponseEntity<?> createBudget(
            @RequestBody BudgetRequestDTO budgetRequestDTO
    ) {
        return VsResponseUtil.success(
                budgetService.create(budgetRequestDTO)
        );
    }

    // GET ALL
    @GetMapping("/user/budget")
    public ResponseEntity<?> getAll() {
        return VsResponseUtil.success(
                budgetService.getAll()
        );
    }

    // UPDATE LIMIT
    @PutMapping("/user/budget/{id}")
    public ResponseEntity<?> updateBudget(
            @PathVariable Long id,
            @RequestBody BudgetRequestDTO request
    ) {
        return VsResponseUtil.success(
                budgetService.setBudget(id, request.getLimitAmount())
        );
    }


    // DELETE
    @DeleteMapping("/user/budget/{id}")
    public ResponseEntity<?> deleteBudget(
            @PathVariable Long id
    ) {
        return VsResponseUtil.success(
                budgetService.deleteBudget(id)
        );
    }

    // REMAINING
    @GetMapping("/user/budget/sum")
    public ResponseEntity<?> sum() {
        return VsResponseUtil.success(
                budgetService.sumExpenseRemaining()
        );
    }
}
