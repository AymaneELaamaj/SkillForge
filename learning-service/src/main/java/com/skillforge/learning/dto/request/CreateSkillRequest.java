package com.skillforge.learning.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateSkillRequest(
        @NotBlank(message = "Le nom de la compétence est requis.") String name
) {}