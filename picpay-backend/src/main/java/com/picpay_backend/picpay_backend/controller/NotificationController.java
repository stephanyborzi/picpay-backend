package com.picpay_backend.picpay_backend.controller;

import com.picpay_backend.picpay_backend.domain.transaction.NotificationRequest;
import com.picpay_backend.picpay_backend.domain.user.User;
import com.picpay_backend.picpay_backend.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
        try {
            User user = new User();
            user.setId(request.getUserId());
            user.setEmail(request.getEmail());

            notificationService.sendNotification(user, request.getMessage());

            return ResponseEntity.ok("Notificação enviada com sucesso!");

        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}