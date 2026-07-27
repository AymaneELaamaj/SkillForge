package com.skillforge.learning.mapper;

import com.skillforge.learning.dto.request.CreateSkillRequest;
import com.skillforge.learning.dto.response.SkillResponse;
import com.skillforge.learning.entity.Skill;
import org.mapstruct.Mapper;

// "uses" indique à MapStruct d'utiliser QuestionMapper pour les sous-listes
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface SkillMapper {
    Skill toEntity(CreateSkillRequest request);
    SkillResponse toResponse(Skill skill);
}