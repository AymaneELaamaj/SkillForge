package com.skillforge.learning.controller;

import com.skillforge.learning.dto.request.CreateQuestionRequest;
import com.skillforge.learning.dto.response.QuestionResponse;
import com.skillforge.learning.entity.Question;
import com.skillforge.learning.mapper.QuestionMapper;
import com.skillforge.learning.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/learning/skills/{skillId}/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    public QuestionController(QuestionService questionService, QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
    }

    @PostMapping
    public ResponseEntity<QuestionResponse> addQuestion(
            @PathVariable Long skillId,
            @Valid @RequestBody CreateQuestionRequest request) {
            
        Question question = questionMapper.toEntity(request);
        Question savedQuestion = questionService.addQuestionToSkill(skillId, question);
        
        return new ResponseEntity<>(questionMapper.toResponse(savedQuestion), HttpStatus.CREATED);
    }
}