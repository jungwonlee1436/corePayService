package com.example.remittance.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepositRequest {
    private String accountNumber; //계좌번호
    private Long amount; //금액

    public void saveAccountNumber(String accountNumber, Long amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }
}
