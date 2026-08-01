package com.skillforge.notification.kafka;

import com.skillforge.notification.event.UserRegisteredEvent;
import com.skillforge.notification.service.WelcomeEmailService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredEventListener {

    private final WelcomeEmailService welcomeEmailService;

    public UserRegisteredEventListener(WelcomeEmailService welcomeEmailService) {
        this.welcomeEmailService = welcomeEmailService;
    }

    @KafkaListener(topics = "${skillforge.kafka.topic.user-registered}")
    public void onUserRegistered(UserRegisteredEvent event) {
        welcomeEmailService.sendWelcomeEmail(event);
    }
}
