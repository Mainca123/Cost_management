package com.example.HeThongQuanLyTaiChinhThongMinh.service.impl;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.ListBankResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Bank;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct.BankMapper;
import com.example.HeThongQuanLyTaiChinhThongMinh.repository.BankRepository;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BankServiceImpl implements BankService {
    private final BankRepository bankRepository;
    private final BankMapper bankMapper;

    @Override
    public List<ListBankResponseDTO> getAll() {
        List<Bank> banks = bankRepository.findAll();
        log.info("Fetched {} banks from database", banks.size());
        for(Bank bank : banks)
            log.info(bank.getShortName());
        return bankMapper.toListBankResponseDTO(banks);
    }
}
