package com.picpay_backend.picpay_backend.dto;

import com.picpay_backend.picpay_backend.domain.transaction.Transaction;
import com.picpay_backend.picpay_backend.domain.user.User;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long senderId, Long receiverId) {
    public Transaction toEntity(User sender, User receiver) {
        return null;
    }
}
