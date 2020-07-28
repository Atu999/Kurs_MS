package com.artur.publisher.controller;

import com.artur.publisher.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final NotificationService notificationService;

    public MessageController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications")
    public String sendStudentNotification(@RequestParam Long studentId) {
        notificationService.sendStudentNotification(studentId);
        return "Wiadomość została wysłana do studenta o id: " + studentId;
    }
}
