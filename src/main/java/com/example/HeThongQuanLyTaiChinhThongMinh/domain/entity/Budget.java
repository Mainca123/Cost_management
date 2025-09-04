package com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "budget")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;

    private Double amountLimit;
    private Date startDate;
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
