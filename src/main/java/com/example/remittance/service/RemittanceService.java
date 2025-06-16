package com.example.remittance.service;

import com.example.remittance.domain.Account;
import com.example.remittance.domain.Transaction;
import com.example.remittance.domain.TransactionType;
import com.example.remittance.dto.DepositRequest;
import com.example.remittance.dto.WithdrawRequest;
import com.example.remittance.repository.AccountRepository;
import com.example.remittance.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
/*
* 1.계좌정보 조회
* 2.입금
* 3.출금
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

    //3.출금
    public void withdraw(WithdrawRequest request) {
        Account account = accountRepository.findByAccountNumber(request.accountNumber())
                .orElseThrow(() -> new RuntimeException("계좌를 찾을 수 없습니다."));

        LocalDate today = LocalDate.now();

        // 일자 초기화
        if (account.getLastWithdrawnDate() == null || !account.getLastWithdrawnDate().equals(today)) {
            account.saveDailyWithdrawnAmount(0L);
            account.saveLastWithdrawnDate(today);
        }

        // 한도 체크
        long newTotal = account.getDailyWithdrawnAmount() + request.amount();
        if (newTotal > 1_000_000L) {
            throw new RuntimeException("일일 출금 한도(1,000,000원)를 초과했습니다.");
        }

        // 잔액 부족 체크
        if (account.getBalance() < request.amount()) {
            throw new RuntimeException("잔액이 부족합니다.");
        }

        // 출금 처리
        // 잔액 저장
        account.minusBalance(account.getBalance(), request.amount());
        //출금 한도 저장
        account.saveDailyWithdrawnAmount(newTotal);

        // 거래 기록
        Transaction transaction = Transaction.builder()
                .account(account)
                .type(TransactionType.WITHDRAW)
                .amount(request.amount())
                .fee(0L)
                .createdAt(LocalDateTime.now())
                .build();

        transactionRepository.save(transaction);
        accountRepository.save(account);
    }

    //4.송금
}
