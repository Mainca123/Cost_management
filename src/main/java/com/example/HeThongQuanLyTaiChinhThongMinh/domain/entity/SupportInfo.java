package com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "support_Infor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupportInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long supportId;

    private String fbLink;
    private String email;
    private Integer phone;
}
