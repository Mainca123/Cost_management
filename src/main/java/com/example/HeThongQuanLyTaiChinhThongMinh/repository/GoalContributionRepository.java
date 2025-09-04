package com.example.HeThongQuanLyTaiChinhThongMinh.repository;

import com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity.GoalContribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoalContributionRepository extends JpaRepository<GoalContribution, Long> {
}