package com.skillforge.learning.dto.response;

public record AssessmentResponse(
        Long id,
        Long userId,
        SkillResponse skill 
) {}