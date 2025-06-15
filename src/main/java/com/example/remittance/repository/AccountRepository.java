package com.example.remittance.repository;

import com.example.remittance.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
/*
* 출금/이체할 때 계좌번호로 찾기
 * */
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
}
