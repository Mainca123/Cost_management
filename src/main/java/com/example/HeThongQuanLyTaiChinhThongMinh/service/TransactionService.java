package com.example.HeThongQuanLyTaiChinhThongMinh.service;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.CreateTransactionRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.TransactionResponseDTO;

import java.util.List;

public interface TransactionService {
    String createTransaction(CreateTransactionRequestDTO createTransactionRequestDTO);
    List<TransactionResponseDTO> getAll();
}
