package com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "saving_goal")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SavingGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long goalId;

    private String title;
    private Double targetAmount;
    private Double currentAmount;
    private Date targetDate;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "goal")
    private List<GoalContribution> contributions;
}
