package com.artur.notifications.service;

import com.artur.notifications.dto.EmailDto;
import com.artur.notifications.dto.NotificationInfoDto;

import javax.mail.MessagingException;

public interface EmailSender {

    void sendEmails(NotificationInfoDto notificationInfo);

    void sendEmail(EmailDto emailDto) throws MessagingException;
}
