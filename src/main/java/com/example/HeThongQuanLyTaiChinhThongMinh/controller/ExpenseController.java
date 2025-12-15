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

    @PostMapping("/user/expense")
    public ResponseEntity<?> createExpense(@RequestBody ExpenseRequestDTO expenseRequestDTO){
        return VsResponseUtil.success(expenseService.createExpense(expenseRequestDTO));
    }

    @GetMapping("/user/expense")
    public ResponseEntity<?> getAll(){
        return VsResponseUtil.success(expenseService.getAll());
    }

    @GetMapping("/user/expense/sum")
    public ResponseEntity<?> sum(){
        return VsResponseUtil.success(expenseService.sumExpenseByBudget());
    }

    @GetMapping("/user/expense/year")
    public ResponseEntity<?> getByYear(@RequestParam long year){
        return VsResponseUtil.success(expenseService.statistical(year));
    }

}
