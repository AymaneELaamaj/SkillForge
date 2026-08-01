package com.skillforge.learning.kafka;

import com.skillforge.learning.event.UserRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserRegisteredEventListener {

    private static final Logger log = LoggerFactory.getLogger(UserRegisteredEventListener.class);

    @KafkaListener(topics = "${skillforge.kafka.topic.user-registered}")
    public void onUserRegistered(UserRegisteredEvent event) {
        log.info(
                "Received UserRegisteredEvent for userId={} username={} registeredAt={}",
                event.userId(),
                event.username(),
                event.registeredAt()
        );
    }
}
