package com.example.remittance.controller;

import com.example.remittance.dto.DepositRequest;
import com.example.remittance.dto.TransactionResponse;
import com.example.remittance.dto.WithdrawRequest;
import com.example.remittance.service.RemittanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class AccountController {
    private final RemittanceService remittanceService;

    @GetMapping("/")
    public String hello() {
        return "Hello, world!";
    }

    //입금
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestBody DepositRequest request) {
        remittanceService.deposit(request);
        return ResponseEntity.ok("입금이 완료되었습니다.");
    }
    //출금
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestBody WithdrawRequest request) {
        remittanceService.withdraw(request);
        return ResponseEntity.ok("출금이 완료되었습니다.");
    }

    //거래내역조회
    @GetMapping("/{accountNumber}/transactions")
    public ResponseEntity<List<TransactionResponse>> getTransactions(@PathVariable String accountNumber) {
        List<TransactionResponse> transactions = remittanceService.getTransactions(accountNumber);
        return ResponseEntity.ok(transactions);
    }

}
