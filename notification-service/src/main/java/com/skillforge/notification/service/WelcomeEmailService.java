package com.skillforge.notification.service;

import com.skillforge.notification.event.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WelcomeEmailService {

    private static final Logger log = LoggerFactory.getLogger(WelcomeEmailService.class);

    public void sendWelcomeEmail(UserRegisteredEvent event) {
        log.info(
                "Simulated welcome email sent to {} ({}) for userId={}",
                event.email(),
                event.username(),
                event.userId()
        );
    }
}
