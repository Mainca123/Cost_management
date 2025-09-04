package com.example.HeThongQuanLyTaiChinhThongMinh.repository;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.BankUser;
import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.BankUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankUserRepository extends JpaRepository<BankUser, BankUserId> {
    @Query("SELECT b FROM BankUser b WHERE b.user.userId = :id")
    List<BankUser> findAllByUserId(Long id);

    @Query("SELECT b FROM BankUser b WHERE b.user.userId = :userId AND b.bank.bankId = :bankId")
    Optional<BankUser> findByUserIdAndBankId(Long userId, Long bankId);
}