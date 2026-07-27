package com.skillforge.identity.dto.response;

public record UserResponse(
        Long id,
        String username,
        String email
) {}