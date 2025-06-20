package com.example.remittance.service;

import com.example.remittance.domain.Account;
import com.example.remittance.domain.Transaction;
import com.example.remittance.dto.TransferRequest;
import com.example.remittance.repository.AccountRepository;
import com.example.remittance.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


/*
   이체단위테스트
 */
@SpringBootTest
class RemittanceServiceUnitTest {


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
    void 이체_성공_테스트() {
        // Given
        String fromAcc = "1111";
        String toAcc = "2222";
        long amount = 100_000L;
        long fee = 1_000L; // 1%

        Account sender = Account.builder()
                .accountNumber(fromAcc)
                .balance(500_000L)
                .dailyWithdrawnAmount(0L)
                .lastWithdrawnDate(LocalDate.now())
                .build();

        Account receiver = Account.builder()
                .accountNumber(toAcc)
                .balance(200_000L)
                .build();

        when(accountRepository.findByAccountNumber(fromAcc)).thenReturn(Optional.of(sender));
        when(accountRepository.findByAccountNumber(toAcc)).thenReturn(Optional.of(receiver));

        TransferRequest request = new TransferRequest(fromAcc, toAcc, amount);

        // When
        remittanceService.transfer(request);

        // Then
        assertEquals(399_000L, sender.getBalance()); // 500,000 - 100,000 - 1,000
        assertEquals(300_000L, receiver.getBalance()); // 200,000 + 100,000
        assertEquals(100_000L, sender.getDailyWithdrawnAmount());

        verify(transactionRepository, times(2)).save(any(Transaction.class));
        verify(accountRepository).save(sender);
        verify(accountRepository).save(receiver);
    }
}
