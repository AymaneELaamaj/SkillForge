package com.skillforge.learning.mapper;

import com.skillforge.learning.dto.request.CreateAssessmentRequest;
import com.skillforge.learning.dto.response.AssessmentResponse;
import com.skillforge.learning.entity.Assessment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

// On utilise SkillMapper pour traduire l'entité Skill imbriquée en SkillResponse
@Mapper(componentModel = "spring", uses = {SkillMapper.class})
public interface AssessmentMapper {

    // On ignore "skill" à la création car on va le chercher via le Service
    Assessment toEntity(CreateAssessmentRequest request);

    AssessmentResponse toResponse(Assessment assessment);
}