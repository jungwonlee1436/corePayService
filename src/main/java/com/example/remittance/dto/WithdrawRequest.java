// dto/WithdrawRequest.java
package com.example.remittance.dto;
/*
* 출금만들기
*
* */
public record WithdrawRequest(String accountNumber, Long amount) {}
