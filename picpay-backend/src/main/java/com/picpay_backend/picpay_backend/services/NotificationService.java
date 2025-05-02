package com.picpay_backend.picpay_backend.services;

import com.picpay_backend.picpay_backend.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private RestTemplate restTemplate;
    private String email;
    private String message;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void sendNotification(User user, String message) {
        String email = user.getEmail();
        try {

            String url = "https://util.devi.tools/api/v1/notify";

            Map<String, String> body = new HashMap<>();
            body.put("userId", String.valueOf(user.getId()));
            body.put("email", email);
            body.put("message", message);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(body);

            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                Map<String, Object> responseBody = response.getBody();
                System.out.println("Notificação enviada com sucesso. Detalhes: " + responseBody);
            } else {
                System.err.println("Falha ao enviar notificação. Status: " + response.getStatusCode());
            }

        } catch (RestClientException e) {
            System.err.println("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}