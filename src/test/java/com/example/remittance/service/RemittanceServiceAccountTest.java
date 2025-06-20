package com.example.remittance.service;

import com.example.remittance.domain.Account;
import com.example.remittance.repository.AccountRepository;
import com.example.remittance.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RemittanceServiceAccountTest {

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
    void 계좌_등록_성공() {
        // Given
        String accountNumber = "1234567890";
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        // When
        remittanceService.registerAccount(accountNumber);

        // Then
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void 계좌_등록_실패_중복() {
        // Given
        String accountNumber = "1234567890";
        when(accountRepository.findByAccountNumber(accountNumber))
                .thenReturn(Optional.of(new Account()));

        // When + Then
        RuntimeException e = assertThrows(RuntimeException.class, () ->
                remittanceService.registerAccount(accountNumber));
        assertTrue(e.getMessage().contains("이미 존재하는 계좌"));
    }

    @Test
    void 계좌_삭제_성공() {
        // Given
        String accountNumber = "1234567890";
        Account account = Account.builder().accountNumber(accountNumber).build();
        when(accountRepository.findByAccountNumber(accountNumber))
                .thenReturn(Optional.of(account));

        // When
        remittanceService.deleteAccount(accountNumber);

        // Then
        verify(accountRepository).delete(account);
    }

    @Test
    void 계좌_삭제_실패_존재하지_않음() {
        // Given
        String accountNumber = "9999999999";
        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

        // When + Then
        RuntimeException e = assertThrows(RuntimeException.class, () ->
                remittanceService.deleteAccount(accountNumber));
        assertTrue(e.getMessage().contains("존재하지 않는 계좌"));
    }
}
