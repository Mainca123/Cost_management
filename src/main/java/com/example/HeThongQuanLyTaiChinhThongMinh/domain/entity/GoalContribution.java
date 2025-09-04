package com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "goal_contribution")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoalContribution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contributionId;

    private Double amount;
    private Date date;
    private String note;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private SavingGoal goal;
}
