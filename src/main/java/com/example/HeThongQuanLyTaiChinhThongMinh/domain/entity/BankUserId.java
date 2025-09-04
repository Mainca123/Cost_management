package com.example.HeThongQuanLyTaiChinhThongMinh.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankUserId implements Serializable {
    private Long bankId;
    private Long userId;

    // equals() và hashCode()
}
