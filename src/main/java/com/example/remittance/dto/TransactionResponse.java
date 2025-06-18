package com.example.remittance.dto;

import com.example.remittance.domain.TransactionType;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        TransactionType type,
        Long amount,
        Long fee,
        LocalDateTime createdAt
) {}
