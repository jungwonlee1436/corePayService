package com.example.remittance.service;

import com.example.remittance.domain.Account;
import com.example.remittance.domain.Transaction;
import com.example.remittance.domain.TransactionType;
import com.example.remittance.dto.DepositRequest;
import com.example.remittance.repository.AccountRepository;
import com.example.remittance.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
/*
* 1.계좌정보 조회
* 2.입금
* */
@Service
@RequiredArgsConstructor
public class RemittanceService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    //1. 계좌정보 조회
    public Long getAccountBalance(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("계좌 없음"));
        return account.getBalance();
    }

    //2. 입금
    public void deposit(DepositRequest request) {
        Account account = accountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("계좌를 찾을 수 없습니다."));

        // 입금
        account.plusBalance(request.getAmount());

        // 거래 내역 저장
        Transaction transaction = Transaction.builder()
                .account(account)
                .type(TransactionType.DEPOSIT)
                .amount(request.getAmount())
                .fee(0L)
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);
        accountRepository.save(account); // 변경된 계좌 저장
    }

}
