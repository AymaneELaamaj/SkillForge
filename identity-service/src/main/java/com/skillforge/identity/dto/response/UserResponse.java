package com.skillforge.identity.dto.response;

import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        String email,
        Set<String> roles
) {}
