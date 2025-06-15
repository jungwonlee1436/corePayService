package com.example.remittance.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ownerName;

    @Column(unique = true)
    private String accountNumber;

    private Long balance = 0L;

    // 출금 일일 한도 관련
    private Long dailyWithdrawnAmount = 0L;

    private LocalDate lastWithdrawnDate;
}
