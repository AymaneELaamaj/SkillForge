package com.skillforge.learning.event;

import java.time.Instant;
import java.util.Set;

public record UserRegisteredEvent(
        Long userId,
        String username,
        String email,
        Set<String> roles,
        Instant registeredAt
) {
}
