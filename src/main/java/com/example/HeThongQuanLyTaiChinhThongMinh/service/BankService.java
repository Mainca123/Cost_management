package com.example.HeThongQuanLyTaiChinhThongMinh.service;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.ListBankResponseDTO;

import java.util.List;

public interface BankService {
    List<ListBankResponseDTO> getAll();
}
