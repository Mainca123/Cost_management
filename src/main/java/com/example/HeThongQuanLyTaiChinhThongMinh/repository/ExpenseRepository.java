package com.example.HeThongQuanLyTaiChinhThongMinh.repository;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserId(Long id);

    List<Expense> findByUserIdOrderByIdDesc(Long id);

    @Query(
            "SELECT COALESCE(SUM(e.amount), 0) " +
                    "FROM Expense e " +
                    "WHERE e.user.id = :userId " +
                    "AND e.spentAt BETWEEN :start AND :end " +
                    "AND e.deletedAt IS NULL"
    )
    Double sumExpenseByUserAndDateRange(
            Long userId,
            LocalDateTime start,
            LocalDateTime end
    );

    @Query(
            "SELECT MONTH(e.spentAt), COALESCE(SUM(e.amount), 0) " +
                    "FROM Expense e " +
                    "WHERE e.user.id = :userId " +
                    "AND YEAR(e.spentAt) = :year " +
                    "AND e.deletedAt IS NULL " +
                    "GROUP BY MONTH(e.spentAt) " +
                    "ORDER BY MONTH(e.spentAt)"
    )
    List<Object[]> statisticByYear(Long userId, long year);

}
