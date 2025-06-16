// dto/TransferRequest.java
package com.example.remittance.dto;

public record TransferRequest(String fromAccountNumber, String toAccountNumber, Long amount) {}
