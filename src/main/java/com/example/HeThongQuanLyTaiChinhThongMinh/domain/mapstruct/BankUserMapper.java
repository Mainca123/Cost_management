package com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.CreateBankUserDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.BankUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankUserMapper {
    BankUser toBankUser(CreateBankUserDTO createBankUserDTO);
}
