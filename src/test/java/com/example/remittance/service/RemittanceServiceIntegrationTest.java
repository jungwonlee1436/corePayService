package com.example.remittance.service;

import com.example.remittance.domain.Account;
import com.example.remittance.dto.DepositRequest;
import com.example.remittance.dto.TransferRequest;
import com.example.remittance.dto.WithdrawRequest;
import com.example.remittance.service.RemittanceService;
import com.example.remittance.repository.AccountRepository;
import com.example.remittance.dto.TransactionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * 통합 테스트
 * 목적 : 등록 -> 입금 -> 출금 -> 이체
 * */
@SpringBootTest
@Transactional
public class RemittanceServiceIntegrationTest {

    @Autowired
    RemittanceService remittanceService;

    @Autowired
    AccountRepository accountRepository;

    String acc1 = "acc123";
    String acc2 = "acc456";

    @BeforeEach
    void setup() {
        remittanceService.registerAccount(acc1);
        remittanceService.registerAccount(acc2);
    }

    @Test
    void 통합_흐름_전체_테스트() {
        // 1. 입금
        remittanceService.deposit(new DepositRequest(acc1, 200_000L));

        Account sender = accountRepository.findByAccountNumber(acc1).orElseThrow();
        assertEquals(200_000L, sender.getBalance());

        // 2. 출금
        remittanceService.withdraw(new WithdrawRequest(acc1, 50_000L));
        sender = accountRepository.findByAccountNumber(acc1).orElseThrow();
        assertEquals(150_000L, sender.getBalance());

        // 3. 이체
        remittanceService.transfer(new TransferRequest(acc1, acc2, 100_000L));
        Account receiver = accountRepository.findByAccountNumber(acc2).orElseThrow();

        assertEquals(49_000L, sender.getBalance());   // 150,000 - 100,000 - 1,000
        assertEquals(100_000L, receiver.getBalance());

        // 4. 거래내역 조회
        List<TransactionResponse> txList = remittanceService.getTransactions(acc1);
        assertEquals(3, txList.size()); // 입금 + 출금 + 이체(보낸쪽)

        List<TransactionResponse> rxList = remittanceService.getTransactions(acc2);
        assertEquals(1, rxList.size()); // 이체(받은쪽)
    }
}

