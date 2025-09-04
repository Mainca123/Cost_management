package com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Column(length = 100)
    private String fullName;

    @Column(length = 15)
    private String gender;

    @Column(length = 15)
    private String phone;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    private Integer role;

    private Date deletedAt;

    // Relationships
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Category> categories;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SavingGoal> savingGoals;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BankUser> bankUsers;

    // Getter & Setter
}