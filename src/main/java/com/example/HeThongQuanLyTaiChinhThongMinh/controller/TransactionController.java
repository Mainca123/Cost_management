package com.example.HeThongQuanLyTaiChinhThongMinh.controller;

import com.example.HeThongQuanLyTaiChinhThongMinh.base.RestApiV1;
import com.example.HeThongQuanLyTaiChinhThongMinh.base.VsResponseUtil;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestApiV1
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/transactions")
    public ResponseEntity<?> getAllTransaction(){
        return VsResponseUtil.success(transactionService.getAll());
    }
}
