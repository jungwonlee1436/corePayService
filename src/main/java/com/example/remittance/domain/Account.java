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

    //출금
    public void minusBalance(long bal, long amnt) {
        balance = bal - amnt;
    }

    //출금일 한도 저장
    public void saveDailyWithdrawnAmount(long l) {
        this.dailyWithdrawnAmount = l;
    }

    //출금인 날짜 저장
    public void saveLastWithdrawnDate(LocalDate today) {
        this.lastWithdrawnDate = today;
    }
}
