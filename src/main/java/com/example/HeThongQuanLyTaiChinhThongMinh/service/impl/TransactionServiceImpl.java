package com.example.HeThongQuanLyTaiChinhThongMinh.service.impl;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.CreateTransactionRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.TransactionResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Category;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Transaction;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.User;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct.TransactionMapper;
import com.example.HeThongQuanLyTaiChinhThongMinh.repository.TransactionRepository;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.BudgetService;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.CategoryService;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.TransactionService;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final UserService userService;
    private final CategoryService categoryService;
    private final BudgetService budgetService;

    @Override
    public String createTransaction(CreateTransactionRequestDTO createTransactionRequestDTO) {
        try {
            Transaction transaction = transactionMapper.toTransaction(createTransactionRequestDTO);
            User user = userService
                    .getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
            transaction.setUser(user);
            Category category = categoryService.getById(createTransactionRequestDTO.getCategoryId());
            transaction.setCategory(category);
            transaction.setDate(LocalDateTime.now());
            transactionRepository.save(transaction);
            budgetService.setAmountLimit(category.getCategoryId(), createTransactionRequestDTO.getAmount());
            return "SUCCESS";
        } catch (RuntimeException e) {
            throw new RuntimeException(":error.create.transaction." + e);
        }
    }

    @Override
    public List<TransactionResponseDTO> getAll() {
        User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Transaction> transactions = transactionRepository.findAllByUserId(user.getUserId());
        return transactionMapper.toTransactionResponse(transactions);
    }
}
