package com.example.remittance.repository;

import com.example.remittance.domain.Account;
import com.example.remittance.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
/*
* 거래내역조회
* */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountOrderByCreatedAtDesc(Account account);
}
