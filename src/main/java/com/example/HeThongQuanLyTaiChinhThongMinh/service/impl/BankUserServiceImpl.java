package com.example.HeThongQuanLyTaiChinhThongMinh.service.impl;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.CreateBankUserDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.BankUserInforResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.ListBankResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Bank;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.BankUser;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.User;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct.BankUserMapper;
import com.example.HeThongQuanLyTaiChinhThongMinh.repository.BankRepository;
import com.example.HeThongQuanLyTaiChinhThongMinh.repository.BankUserRepository;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.BankUserService;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BankUserServiceImpl implements BankUserService {

    private final BankUserRepository bankUserRepository;
    private final BankUserMapper bankUserMapper;
    private final BankRepository bankRepository;
    private final UserService userService;

    @Override
    public String createBankUser(CreateBankUserDTO createBankUserDTO) {
        try {
            var bankUser = bankUserMapper.toBankUser(createBankUserDTO);
            var bank = bankRepository.findById(createBankUserDTO.getId().getBankId())
                    .orElseThrow(() -> new RuntimeException("Bank not found"));
            var user = userService
                    .getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

            bankUser.setBank(bank);
            bankUser.setUser(user);

            bankUserRepository.save(bankUser);

            return "SUCCESS";
        } catch (RuntimeException e) {
            throw new RuntimeException("error.create.bank.user." + e.getMessage(), e);
        }
    }
    @Override
    public List<ListBankResponseDTO> getBankUser() {
        User user = userService.getUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );
        List<BankUser> bankUsers = bankUserRepository.findAllByUserId(user.getUserId());
        return bankUsers.stream()
                .map(bu -> new ListBankResponseDTO(
                        bu.getBank().getBankId(),
                        bu.getBank().getShortName()
                ))
                .toList();
    }

    @Override
    public BankUserInforResponseDTO getBankUser(Long bankId) {
        try {
            User user = userService.getUserByUsername(
                    SecurityContextHolder.getContext().getAuthentication().getName()
            );
            BankUser bankUser = bankUserRepository.findByUserIdAndBankId(user.getUserId(), bankId).orElseThrow(()
                    -> new RuntimeException("not.found"));
            return BankUserInforResponseDTO.builder()
                    .accountNumber(bankUser.getAccountNumber())
                    .accountName(bankUser.getAccountName())
                    .accessToken(bankUser.getAccessToken())
                    .refreshToken(bankUser.getRefreshToken())
                    .clientId(bankUser.getClientId())
                    .clientSecret(bankUser.getClientSecret())
                    .build();
        } catch (RuntimeException e){
           throw  new RuntimeException(e.getMessage());
        }
    }
}
