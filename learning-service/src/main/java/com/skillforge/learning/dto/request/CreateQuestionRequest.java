package com.skillforge.learning.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateQuestionRequest(
        @NotBlank(message = "Le texte de la question est requis.") String text
) {}