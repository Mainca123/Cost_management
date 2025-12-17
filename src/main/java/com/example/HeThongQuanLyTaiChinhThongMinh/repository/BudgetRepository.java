package com.example.HeThongQuanLyTaiChinhThongMinh.repository;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {

    @Query("SELECT b FROM Budget b WHERE b.user.id = :userId " +
            "AND b.startDate <= CURRENT_DATE " +
            "AND b.endDate >= CURRENT_DATE " +
            "AND b.deletedAt IS NULL " )
    Optional<Budget> findActiveBudgetByUsername(Long userId);

    @Query("SELECT b FROM Budget b " +
            "WHERE b.user.id = :userId " +
            "AND b.endDate >= CURRENT_DATE " +
            "AND b.endDate >= :newStartDate " +
            "AND b.startDate <= :newStartDate " +
            "AND b.deletedAt IS NULL")
    Optional<Budget> findBudgetForStartDate(Long userId, LocalDate newStartDate);

    @Query("SELECT b FROM Budget b " +
            "WHERE b.user.id = :userId " +
            "AND b.endDate >= CURRENT_DATE " +
            "AND b.startDate <= :newEndDate " +
            "AND b.endDate >= :newEndDate " +
            "AND b.deletedAt IS NULL")
    Optional<Budget> findBudgetForEndDate(Long userId, LocalDate newEndDate);

    @Query("SELECT b FROM Budget b " +
            "WHERE b.user.id = :userId " +
            "AND b.endDate >= CURRENT_DATE " +
            "AND b.deletedAt IS NULL")
    List<Budget> getAllByUserId(Long userId);

    @Query("SELECT b FROM Budget b " +
            "WHERE b.endDate >= :now " +
            "AND b.startDate <= :now " +
            "AND b.user.id = :userId " +
            "AND b.deletedAt IS NULL")
    Optional<Budget> findByDate(long userId, LocalDate now);

    Optional<Budget> findByIdAndUser_IdAndDeletedAtIsNull(Long id, Long userId);
}