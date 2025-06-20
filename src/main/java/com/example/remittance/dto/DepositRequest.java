package com.example.remittance.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {

    @Column(unique = true, nullable = false)
    private String accountNumber; //계좌번호
    private Long amount; //금액

    public void saveAccountNumber(String accountNumber, Long amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }
}
