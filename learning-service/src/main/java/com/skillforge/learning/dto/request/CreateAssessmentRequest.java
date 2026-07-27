package com.skillforge.learning.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateAssessmentRequest(
        @NotNull(message = "L'ID de l'utilisateur est requis.") Long userId,
        @NotNull(message = "L'ID de la compétence est requis.") Long skillId
) {}