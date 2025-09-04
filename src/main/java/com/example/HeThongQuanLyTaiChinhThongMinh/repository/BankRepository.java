package com.example.HeThongQuanLyTaiChinhThongMinh.repository;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.Bank;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, Long> {
}