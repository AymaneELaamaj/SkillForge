package com.skillforge.learning.dto;

public record CreateAssessmentRequest(
        Long userId,
        Long skillId
) {}
