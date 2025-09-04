package com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.CreateTransactionRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.TransactionResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Transaction;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toTransaction(CreateTransactionRequestDTO createTransactionRequestDTO);
    List<TransactionResponseDTO> toTransactionResponse(List<Transaction> transactions);
}
