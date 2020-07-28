package com.artur.receiver.controller;

import com.artur.receiver.model.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    private final RabbitTemplate rabbitTemplate;

    public MessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    //Aby użyć tej metody musisz za komentować @RabbitListener, jeśli tego nie zrobisz to
    //wszystkie wiadomości z kolejki "kurs" zostaną "skradzione" przez listenera.
    @GetMapping("/notification")
    public ResponseEntity<Notification> receiveNotification() {
        Notification notification = rabbitTemplate.
                receiveAndConvert("kurs", ParameterizedTypeReference.forType(Notification.class));
        if (notification != null) {
            return ResponseEntity.ok(notification);
        }
        return ResponseEntity.noContent().build();
    }

    @RabbitListener(queues = "kurs")
    public void listenerMessage(Notification notification) {
        //warto dodać walidacje czy to jest obiek Notification
        System.out.println("Email: " +notification.getEmail() + " Title: " + notification.getTitle() + " Body: " + notification.getBody());
    }
}
