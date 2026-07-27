package com.skillforge.learning.mapper;

import com.skillforge.learning.dto.request.CreateQuestionRequest;
import com.skillforge.learning.dto.response.QuestionResponse;
import com.skillforge.learning.entity.Question;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question toEntity(CreateQuestionRequest request);
    QuestionResponse toResponse(Question question);
}