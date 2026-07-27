package com.skillforge.learning.dto.response;

import java.util.List;

// Ce DTO contient la liste des questions sous forme de DTOs, pas d'Entités !
public record SkillResponse(
        Long id,
        String name,
        List<QuestionResponse> questions
) {}