package com.picpay_backend.picpay_backend.repositories;

import com.picpay_backend.picpay_backend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDocument(String documento);
    Optional<User> findUserById(Long id);
 }
