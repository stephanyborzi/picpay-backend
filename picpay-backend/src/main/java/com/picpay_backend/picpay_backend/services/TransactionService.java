package com.picpay_backend.picpay_backend.services;

import com.picpay_backend.picpay_backend.domain.transaction.Transaction;
import com.picpay_backend.picpay_backend.domain.user.User;
import com.picpay_backend.picpay_backend.dto.TransactionDTO;
import com.picpay_backend.picpay_backend.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public void createTransaction(TransactionDTO transactionDTO) throws Exception {
        try {
            User sender = this.userService.findUserById(transactionDTO.senderId());
            User receiver = this.userService.findUserById(transactionDTO.receiverId());

            userService.validateTransaction(sender, transactionDTO.value());

            boolean isAuthorized = authorizeTransaction(sender, transactionDTO.value());
            if (!isAuthorized) {
                throw new IllegalStateException("Transação não autorizada pelo serviço externo.");
            }

            Transaction newTransaction = new Transaction();
            newTransaction.setAmount(transactionDTO.value());
            newTransaction.setSender(sender);
            newTransaction.setReceiver(receiver);
            newTransaction.setTimestamp(LocalDateTime.now());
            sender.setBalance(receiver.getBalance().subtract(transactionDTO.value()));
            receiver.setBalance(receiver.getBalance().subtract(transactionDTO.value()));

            this.repository.save(newTransaction);
            this.userService.saveUser(sender);
            this.userService.saveUser(receiver);


        } catch (Exception e) {
            throw new Exception("Erro ao criar a transação: " + e.getMessage(), e);
        }
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        try {
            String url = String.format(
                    "https://util.devi.tools/api/v2/authorize?userId=%d&value=%s",
                    sender.getId(),
                    value.toString()
            );

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url, HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                return Boolean.TRUE.equals(responseBody.get("authorize"));
            }

        } catch (RestClientException e) {
            System.err.println("Erro durante a comunicação com o serviço externo: " + e.getMessage());
        }
        return false;
    }
}