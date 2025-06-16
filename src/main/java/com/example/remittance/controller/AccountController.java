package com.example.remittance.controller;

import com.example.remittance.dto.DepositRequest;
import com.example.remittance.dto.WithdrawRequest;
import com.example.remittance.service.RemittanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;


@RestController
@RequiredArgsConstructor
public class AccountController {
    private final RemittanceService remittanceService;

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


}
