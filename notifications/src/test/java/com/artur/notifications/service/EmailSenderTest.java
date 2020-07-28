package com.artur.notifications.service;

import com.artur.notifications.dto.EmailDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

@SpringBootTest
public class EmailSenderTest {

    @Autowired
    EmailSender emailSender;

    @Test
    public void send_email_test() throws MessagingException {
        EmailDto emailDto = EmailDto.builder()
                .to("atuuu121@gmail.com")
                .title("Hejo!!!")
                .content("Test1!!!")
                .build();
        emailSender.sendEmail(emailDto);
    }


}