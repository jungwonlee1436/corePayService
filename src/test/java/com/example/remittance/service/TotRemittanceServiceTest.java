package com.example.remittance.service;

import com.example.remittance.dto.DepositRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TotRemittanceServiceTest {

    @Autowired
    RemittanceService remittanceService;

    //입금 통합 테스트
    @Test
    void depositTest(){
        String accountNumber = "1234567890";
        Long amount = 10000L;
        // 1. 테스트용 계좌가 먼저 있어야 함 (실제 DB에 있어야 성공)
        DepositRequest request = new DepositRequest();
        request.saveAccountNumber(accountNumber, amount);

        // 2. 입금 실행
        remittanceService.deposit(request);

        // 3. 결과 검증
        Long balance = remittanceService.getAccountBalance(accountNumber);
        assertEquals(10000L, balance);  // ✅ 기대값과 실제값이 같은지 확인
    }
}