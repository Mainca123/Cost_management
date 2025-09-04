package com.example.HeThongQuanLyTaiChinhThongMinh.controller;

import com.example.HeThongQuanLyTaiChinhThongMinh.base.RestApiV1;
import com.example.HeThongQuanLyTaiChinhThongMinh.base.VsResponseUtil;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.CreateBankUserDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.CreateTransactionRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.BankService;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.BankUserService;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestApiV1
@RequiredArgsConstructor
public class BankController {
    private final BankService bankService;
    private final BankUserService bankUserService;
    private final TransactionService transactionService;

    @GetMapping("/banks")
    public ResponseEntity<?> getAllBank(){
        return VsResponseUtil.success(bankService.getAll());
    }

    @GetMapping("/bank/user")
    public ResponseEntity<?> getAllBankUser(){
       return VsResponseUtil.success(bankUserService.getBankUser());
    }

    @PostMapping("/bank/user")
    public ResponseEntity<?> createBankUser(@RequestBody @Valid CreateBankUserDTO createBankUserDTO){
        return VsResponseUtil.success(bankUserService.createBankUser(createBankUserDTO));
    }

    @PostMapping("/bank/transaction")
    public ResponseEntity<?> createBTransaction(
            @RequestBody @Valid CreateTransactionRequestDTO createTransactionRequestDTO){
        return VsResponseUtil.success(transactionService.createTransaction(createTransactionRequestDTO));
    }

    @GetMapping("/bank/user/information")
    public ResponseEntity<?> getBankUserInfor(@RequestParam Long bankId){
        return VsResponseUtil.success(bankUserService.getBankUser(bankId));
    }
}
