package com.picpay_backend.picpay_backend.services;

import com.picpay_backend.picpay_backend.domain.user.User;
import com.picpay_backend.picpay_backend.domain.user.UserType;
import com.picpay_backend.picpay_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) throws Exception {
        if (sender.getUserType().equals(UserType.MERCHANT)) {
            throw new Exception("Usuário do tipo lojista não realiza transações bancárias.");
        }
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new Exception("Saldo insuficiente para realizar a transação.");
        }
    }

    public User findUserById(Long id) throws Exception {
        return this.repository.findUserById(id).orElseThrow(() -> new Exception("Id do usuario nao encontrado"));
    }

    public void saveUser(User user){
        this.repository.save(user);
    }
}
