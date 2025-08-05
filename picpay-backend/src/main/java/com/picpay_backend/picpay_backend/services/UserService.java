package com.picpay_backend.picpay_backend.services;

import com.picpay_backend.picpay_backend.domain.user.User;
import com.picpay_backend.picpay_backend.domain.user.UserType;
import com.picpay_backend.picpay_backend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType().equals(UserType.MERCHANT)) {
            throw new Exception("Mechant cannot send money in the transaction");
        }
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Balance not enought to the transaction.");
        }
        log.info("Transaction validated for user ID {} with amount {}", sender.getId(), amount);
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("User ID not found"));
    }

    public void saveUser(User user){
        this.repository.save(user);
    }
}
