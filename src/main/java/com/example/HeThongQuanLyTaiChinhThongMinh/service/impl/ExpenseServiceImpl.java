package com.example.HeThongQuanLyTaiChinhThongMinh.service.impl;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.ExpenseRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.ExpenseResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Budget;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Category;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Expense;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.User;
import com.example.HeThongQuanLyTaiChinhThongMinh.repository.ExpenseRepository;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.BudgetService;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.CategoryService;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.ExpenseService;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserService userService;
    private final CategoryService categoryService;
    private final BudgetService budgetService;

    @Override
    public String createExpense(ExpenseRequestDTO expenseRequestDTO) {
        User user =  userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        Category category = categoryService.getById(expenseRequestDTO.getCategoryId());
        LocalDateTime spentAt = expenseRequestDTO.getSpentAt();

        if(expenseRequestDTO.getSpentAt() != null){
            if(expenseRequestDTO.getSpentAt().isAfter(LocalDateTime.now()))
                throw new RuntimeException("error.spent.at");
        } spentAt = LocalDateTime.now();
        try{
            Expense expense = Expense.builder()
                    .user(user)
                    .note(expenseRequestDTO.getNote())
                    .amount(expenseRequestDTO.getAmount())
                    .category(category)
                    .createdAt(LocalDateTime.now())
                    .spentAt(spentAt)
                    .build();
            budgetService.setAmountLimit(expense.getAmount());
            expenseRepository.save(expense);

        } catch (RuntimeException e) {
            throw new RuntimeException("error.create.expense." + e);
        }

        return "SUCCESS";
    }

    @Override
    public List<ExpenseResponseDTO> getAll() {
        List<Expense> expenses = expenseRepository.findByUserIdOrderByIdDesc(
                userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                        .getId());
        return expenses.stream()
                .map(expense ->
                    ExpenseResponseDTO.builder()
                            .id(expense.getId())
                            .amount(expense.getAmount())
                            .note(expense.getNote())
                            .spentAt(expense.getSpentAt())
                            .categoryName(expense.getCategory().getName())
                            .createdAt(expense.getCreatedAt())
                            .build()
                )
                .toList();
    }
    @Override
    public double sumExpenseByBudget() {

        // 1. Check authentication (BẮT BUỘC)
        var auth = SecurityContextHolder.getContext().getAuthentication();
        // 2. Lấy budget hiện tại
        Budget budget = budgetService.findBudgetNow();
        if (budget == null) return 0.0;

        // 3. Lấy user
        User user = userService.getUserByUsername(auth.getName());
        if (user == null) return 0.0;

        // 4. Tính khoảng thời gian
        LocalDateTime start = budget.getStartDate().atStartOfDay();
        LocalDateTime end   = budget.getEndDate().plusDays(1).atStartOfDay();

        // 5. SUM (PHẢI CHỐNG NULL)
        Double sum = expenseRepository
                .sumExpenseByUserAndDateRange(user.getId(), start, end);

        return sum == null ? 0.0 : sum;
    }


    @Override
    public List<Double> statistical(long year) {

        User user = userService.getUserByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        );

        // Khởi tạo 12 tháng = 0
        List<Double> result = new ArrayList<>(Collections.nCopies(12, 0.0));

        List<Object[]> raw = expenseRepository.statisticByYear(user.getId(), year);

        for (Object[] row : raw) {
            int month = ((Number) row[0]).intValue();   // 1..12
            double total = ((Number) row[1]).doubleValue();

            result.set(month - 1, total);
        }

        return result;
    }


}
