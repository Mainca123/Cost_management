package com.example.HeThongQuanLyTaiChinhThongMinh.service;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.CreateBankUserDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.BankUserInforResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.ListBankResponseDTO;

import java.util.List;

public interface BankUserService {
    String createBankUser(CreateBankUserDTO createBankUserDTO);
    List<ListBankResponseDTO> getBankUser();
    BankUserInforResponseDTO getBankUser(Long bankId);
}
