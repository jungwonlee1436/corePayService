package com.example.remittance.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositRequest {
    private String accountNumber; //계좌번호
    private Long amount; //금액
}
