package com.example.HeThongQuanLyTaiChinhThongMinh.service.impl;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.BudgetRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.response.BudgetResponseDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Budget;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.User;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct.BudgetMapper;
import com.example.HeThongQuanLyTaiChinhThongMinh.repository.BudgetRepository;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.BudgetService;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final UserService userService;
    private final BudgetRepository budgetRepository;
//    private final BudgetMapper budgetMapper;
//
    @Override
    public String create(BudgetRequestDTO budgetRequestDTO) {
       try {

           User user = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

           if(budgetRequestDTO.getStartDate().isBefore(LocalDate.now()) ||
                   budgetRequestDTO.getStartDate().isAfter(budgetRequestDTO.getEndDate())||
                   budgetRequestDTO.getStartDate().equals(budgetRequestDTO.getEndDate())){
               throw new RuntimeException("error.your.date");
           }

           if(budgetRepository.findBudgetForStartDate(user.getId(), budgetRequestDTO.getStartDate()).isPresent())
               throw new RuntimeException("error.start.date");
           if(budgetRepository.findBudgetForEndDate(user.getId(), budgetRequestDTO.getEndDate()).isPresent())
               throw new RuntimeException("error.end.date");

           Budget budget = Budget.builder()
                   .limitAmount(budgetRequestDTO.getLimitAmount())
                   .startDate(budgetRequestDTO.getStartDate())
                   .endDate(budgetRequestDTO.getEndDate())
                   .user(user)
                   .createdAt(LocalDateTime.now())
                   .build();
           budgetRepository.save(budget);
           return "SUCCESS";
       } catch (RuntimeException e) {
           throw new RuntimeException("error.create.budget." + e);
       }
    }
//    @Override
//    public BudgetRequestDTO getBudgetAmountLimit(Long categoryId) {
//        Budget budget = findByCategoryId(categoryId);
//        return budgetMapper.toBudgetResponse(budget);
//    }
//
//    @Override
//    public Budget findByUserId(Long categoryId) {
//        return budgetRepository.findTopByCategoryOrderByBudgetIdDesc(categoryId).orElseThrow(()
//                -> new RuntimeException("not.found.budget.category.id." + categoryId)
//        );
//    }
//
    @Override
    public String setAmountLimit(Double amount) {

        if (amount == null || amount <= 0) {
            return "SKIP";
        }

        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            return "SKIP";
        }

        User user = userService.getUserByUsername(auth.getName());
        if (user == null) {
            return "SKIP";
        }

        // Tìm budget active – KHÔNG THROW
        Budget budget = budgetRepository
                .findActiveBudgetByUsername(user.getId())
                .orElse(null);

        if (budget == null) {
            // ❗ Chưa có budget → không làm gì
            return "NO_BUDGET";
        }

        Double currentLimit = budget.getLimitAmount();
        if (currentLimit == null) {
            return "SKIP";
        }

        // Trừ ngân sách (không cho âm nếu không muốn)
        double remain = currentLimit - amount;
        budget.setLimitAmount(remain);

        budgetRepository.save(budget);

        return "SUCCESS";
    }


    @Override
    public List<BudgetResponseDTO> getAll() {
        User user =  userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Budget> budgets = budgetRepository.getAllByUserId(user.getId());
        if (budgets.isEmpty()) throw new RuntimeException("not.found.budget.by.user.id." + user.getId());
        try{
            return budgets.stream()
                    .map(budget ->
                            BudgetResponseDTO.builder()
                                    .id(budget.getId())
                                    .startDate(budget.getStartDate())
                                    .endDate(budget.getEndDate())
                                    .limitAmount(budget.getLimitAmount())
                                    .createdAt(budget.getCreatedAt())
                                    .build()
                    )
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException("error.get.all.budget");
        }
    }

    @Override
    public Budget findBudgetNow() {

        var auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByUsername(auth.getName());
        if (user == null) {
            return null;
        }

        return budgetRepository
                .findByDate(user.getId(), LocalDate.now())
                .orElse(null); // not throw
    }


    @Override
    public Double sumExpenseRemaining() {

        Budget budget = findBudgetNow();
        if (budget == null) return 0.0;

        return budget.getLimitAmount() == null
                ? 0.0
                : budget.getLimitAmount();
    }

}
