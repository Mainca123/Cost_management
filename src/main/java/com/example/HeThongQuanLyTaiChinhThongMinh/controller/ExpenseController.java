package com.example.HeThongQuanLyTaiChinhThongMinh.controller;

import com.example.HeThongQuanLyTaiChinhThongMinh.base.RestApiV1;
import com.example.HeThongQuanLyTaiChinhThongMinh.base.VsResponseUtil;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.ExpenseRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestApiV1
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    /* ================= CREATE ================= */
    @PostMapping("/user/expense")
    public ResponseEntity<?> createExpense(
            @RequestBody ExpenseRequestDTO expenseRequestDTO
    ) {
        return VsResponseUtil.success(
                expenseService.createExpense(expenseRequestDTO)
        );
    }

    /* ================= GET ALL ================= */
    @GetMapping("/user/expense")
    public ResponseEntity<?> getAll() {
        return VsResponseUtil.success(
                expenseService.getAll()
        );
    }

    /* ================= GET ONE ================= */
    @GetMapping("/user/expense/{id}")
    public ResponseEntity<?> getOne(
            @org.springframework.web.bind.annotation.PathVariable Long id
    ) {
        return VsResponseUtil.success(
                expenseService.getExpense(id)
        );
    }

    /* ================= UPDATE ================= */
    @org.springframework.web.bind.annotation.PutMapping("/user/expense/{id}")
    public ResponseEntity<?> updateExpense(
            @org.springframework.web.bind.annotation.PathVariable Long id,
            @RequestBody ExpenseRequestDTO expenseRequestDTO
    ) {
        return VsResponseUtil.success(
                expenseService.updateExpense(id, expenseRequestDTO)
        );
    }

    /* ================= DELETE ================= */
    @org.springframework.web.bind.annotation.DeleteMapping("/user/expense/{id}")
    public ResponseEntity<?> deleteExpense(
            @org.springframework.web.bind.annotation.PathVariable Long id
    ) {
        return VsResponseUtil.success(
                expenseService.deleteExpense(id)
        );
    }

    /* ================= SUM BY BUDGET ================= */
    @GetMapping("/user/expense/sum")
    public ResponseEntity<?> sum() {
        return VsResponseUtil.success(
                expenseService.sumExpenseByBudget()
        );
    }

    /* ================= STATISTIC BY YEAR ================= */
    @GetMapping("/user/expense/year")
    public ResponseEntity<?> getByYear(
            @RequestParam long year
    ) {
        return VsResponseUtil.success(
                expenseService.statistical(year)
        );
    }
}

