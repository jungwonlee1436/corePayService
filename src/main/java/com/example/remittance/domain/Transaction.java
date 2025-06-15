package com.example.remittance.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 거래한 계좌
    @ManyToOne
    private Account account;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private Long amount;

    private Long fee; // 수수료 (없으면 0)

    private LocalDateTime createdAt;
}
