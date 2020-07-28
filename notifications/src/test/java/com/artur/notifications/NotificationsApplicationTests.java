package com.artur.notifications;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class NotificationsApplicationTests {

    @Test
    void contextLoads() {

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime.toLocalDate());
        System.out.println(localDateTime.getHour() + ":" + localDateTime.getMinute());

    }

}
