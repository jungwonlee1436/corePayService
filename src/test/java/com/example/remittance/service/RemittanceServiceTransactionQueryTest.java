package com.example.remittance.service;

import com.example.remittance.domain.Account;
import com.example.remittance.domain.Transaction;
import com.example.remittance.domain.TransactionType;
import com.example.remittance.dto.TransactionResponse;
import com.example.remittance.repository.AccountRepository;
import com.example.remittance.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemittanceServiceTransactionQueryTest {

    private RemittanceService remittanceService;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        remittanceService = new RemittanceService(accountRepository, transactionRepository);
    }

    @Test
    void 거래내역_조회_테스트() {
        // Given
        String accountNumber = "1111";
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .balance(500_000L)
                .build();

        when(accountRepository.findByAccountNumber(accountNumber))
                .thenReturn(Optional.of(account));

        Transaction tx1 = Transaction.builder()
                .id(1L)
                .account(account)
                .type(TransactionType.DEPOSIT)
                .amount(100_000L)
                .fee(0L)
                .createdAt(LocalDateTime.now().minusMinutes(5))
                .build();

        Transaction tx2 = Transaction.builder()
                .id(2L)
                .account(account)
                .type(TransactionType.TRANSFER)
                .amount(50_000L)
                .fee(500L)
                .createdAt(LocalDateTime.now())
                .build();

        when(transactionRepository.findByAccountOrderByCreatedAtDesc(account))
                .thenReturn(List.of(tx2, tx1)); // 최신순

        // When
        List<TransactionResponse> responses = remittanceService.getTransactions(accountNumber);

        // Then
        assertEquals(2, responses.size());
        assertEquals(TransactionType.TRANSFER, responses.get(0).type());
        assertEquals(TransactionType.DEPOSIT, responses.get(1).type());

        assertEquals(50_000L, responses.get(0).amount());
        assertEquals(100_000L, responses.get(1).amount());

        verify(transactionRepository).findByAccountOrderByCreatedAtDesc(account);
    }
}
