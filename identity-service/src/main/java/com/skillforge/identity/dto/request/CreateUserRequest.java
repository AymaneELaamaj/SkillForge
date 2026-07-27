package com.skillforge.identity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    String username,

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    String email,

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 50)
    String password

) {}
