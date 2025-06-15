package com.example.remittance.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
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

    @Builder.Default
    private Long balance = 0L;

    // 출금 일일 한도 관련
    @Builder.Default
    private Long dailyWithdrawnAmount = 0L;

    private LocalDate lastWithdrawnDate;

    //입금
    public void plusBalance(long l) {
        this.balance += l;
    }
}
