package com.example.HeThongQuanLyTaiChinhThongMinh.service.impl;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.dto.request.BudgetRequestDTO;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Budget;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Category;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.mapstruct.BudgetMapper;
import com.example.HeThongQuanLyTaiChinhThongMinh.repository.BudgetRepository;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.BudgetService;
import com.example.HeThongQuanLyTaiChinhThongMinh.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

    private final CategoryService categoryService;
    private final BudgetRepository budgetRepository;
    private final BudgetMapper budgetMapper;

    @Override
    public String create(BudgetRequestDTO budgetRequestDTO) {
       try {
           Category category = categoryService.getById(budgetRequestDTO.getCategoryId());
           Budget budget = Budget.builder()
                   .amountLimit(budgetRequestDTO.getAmountLimit())
                   .startDate(budgetRequestDTO.getStartDate())
                   .endDate(budgetRequestDTO.getEndDate())
                   .category(category)
                   .build();
           budgetRepository.save(budget);
           return "SUCCESS";
       } catch (RuntimeException e) {
           throw new RuntimeException("error.create.budget." + e);
       }
    }
    @Override
    public BudgetRequestDTO getBudgetAmountLimit(Long categoryId) {
        Budget budget = findByCategoryId(categoryId);
        return budgetMapper.toBudgetResponse(budget);
    }

    @Override
    public Budget findByCategoryId(Long categoryId) {
        return budgetRepository.findTopByCategoryOrderByBudgetIdDesc(categoryId).orElseThrow(()
                -> new RuntimeException("not.found.budget.category.id." + categoryId)
        );
    }

    @Override
    public String setAmountLimit(Long categoryId, Double amountLimit) {
        try {
            Budget budget = findByCategoryId(categoryId);
            budget.setAmountLimit(budget.getAmountLimit() - amountLimit);
            budgetRepository.save(budget);
            return "SUCCESS";
        } catch (RuntimeException e) {
            throw new RuntimeException("error.set.amount."+e);
        }
    }
}
