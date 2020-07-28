package com.artur.notifications.service;

import com.artur.notifications.dto.NotificationInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMqListener {

    private final EmailSender emailSender;

    public RabbitMqListener(EmailSender emailSender) {
        this.emailSender = emailSender;
    }

    @RabbitListener(queues = "enroll_finish")
    public void handleFinishEnroll(NotificationInfoDto notificationInfo) {
        emailSender.sendEmails(notificationInfo);
    }
}
