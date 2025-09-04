package com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.ListBankResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Bank;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankMapper {
    List<ListBankResponseDTO> toListBankResponseDTO(List<Bank> banks);
}
