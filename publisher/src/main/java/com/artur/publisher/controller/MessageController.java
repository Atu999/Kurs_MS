package com.artur.publisher.controller;

import com.artur.publisher.model.Notification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final RabbitTemplate rabbitTemplate;

    public MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/notification")
    public String sendNotification(@RequestBody Notification notification) {
        rabbitTemplate.convertAndSend("kurs", notification);
        return "Notyfikacja wysłana na RabbitMq!";
    }
}
