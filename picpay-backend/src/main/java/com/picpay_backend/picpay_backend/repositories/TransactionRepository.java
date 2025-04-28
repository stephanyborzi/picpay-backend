package com.picpay_backend.picpay_backend.repositories;

import com.picpay_backend.picpay_backend.domain.transaction.Transaction;
import com.picpay_backend.picpay_backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
