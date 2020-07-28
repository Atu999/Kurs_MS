package com.artur.notifications.service;

import com.artur.notifications.dto.EmailDto;
import com.artur.notifications.dto.NotificationInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailSenderImpl implements EmailSender {

    private final JavaMailSender javaMailSender;

    public EmailSenderImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmails(NotificationInfoDto notificationInfo) {
        String title = "Pamietaj o kursie: " + notificationInfo.getCourseName();
        StringBuilder content = createEmailContent(notificationInfo);
        notificationInfo.getEmails().forEach(email -> {
            try {
                sendEmail(email, title, content.toString());
            } catch (MessagingException e) {
                log.error("Notyfikacja się nie wysłała!!!!!", e);
            }
        });
    }

    private StringBuilder createEmailContent(NotificationInfoDto notificationInfo) {
        StringBuilder content = new StringBuilder();
        content.append("Kurs ");
        content.append(notificationInfo.getCourseName());
        content.append(" rozpoczyna sie ");
        content.append(notificationInfo.getCourseStartDate().toLocalDate());
        content.append("o godzinie: ");
        content.append(notificationInfo.getCourseStartDate().getHour()).append(":").append(notificationInfo.getCourseStartDate().getMinute());
        content.append(". Proszę stawić się 15min wcześniej!");
        content.append("\n");
        content.append("Opis kursu: ");
        content.append(notificationInfo.getCourseDescription());
        content.append("\n");
        content.append("Kurs kończy się ");
        content.append(notificationInfo.getCourseEndDate().getHour()).append(":").append(notificationInfo.getCourseEndDate().getMinute());
        content.append("\n");
        content.append("Czekamy na Ciebie!");
        return content;
    }

    public void sendEmail(EmailDto emailDto) throws MessagingException {
        sendEmail(emailDto.getTo(), emailDto.getTitle(), emailDto.getContent());
    }

    private void sendEmail(String to, String title, String content) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(title);
        mimeMessageHelper.setText(content, false);
        javaMailSender.send(mimeMessage);
    }

}
