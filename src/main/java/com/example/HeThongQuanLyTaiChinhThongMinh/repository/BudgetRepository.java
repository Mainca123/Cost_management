package com.example.HeThongQuanLyTaiChinhThongMinh.repository;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query("SELECT b FROM Budget b WHERE b.category.categoryId = :categoryId ORDER BY b.budgetId DESC LIMIT 1")
    Optional<Budget> findTopByCategoryOrderByBudgetIdDesc(Long categoryId);

}