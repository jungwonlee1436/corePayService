package com.example.remittance.service;

import com.example.remittance.domain.Account;
import com.example.remittance.domain.Transaction;
import com.example.remittance.dto.DepositRequest;
import com.example.remittance.repository.AccountRepository;
import com.example.remittance.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


//단위테스트
class DepositServiceUnitTest {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private RemittanceService remittanceService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        transactionRepository = mock(TransactionRepository.class);
        remittanceService = new RemittanceService(accountRepository, transactionRepository);
    }

    @Test
    void deposit_shouldIncreaseBalanceAndSaveTransaction() {
        // Given
        String accountNumber = "1234567890";
        Long initialBalance = 0L;
        Long depositAmount = 10000L;

        Account account = Account.builder()
                .accountNumber(accountNumber)
                .balance(initialBalance)
                .build();

        when(accountRepository.findByAccountNumber(accountNumber))
                .thenReturn(Optional.of(account));

        DepositRequest request = new DepositRequest();
        request.saveAccountNumber(accountNumber, depositAmount);

        // When
        remittanceService.deposit(request);

        // Then
        assertEquals(depositAmount, account.getBalance());

        // 검증: 거래내역이 저장되었는가?
        verify(transactionRepository, times(1)).save(any(Transaction.class));

        // 검증: 계좌가 업데이트 되었는가?
        verify(accountRepository, times(1)).save(account);
    }
}
