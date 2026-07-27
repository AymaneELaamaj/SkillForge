package com.skillforge.learning.controller;

import com.skillforge.learning.entity.Question;
import com.skillforge.learning.service.QuestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learning/skills/{skillId}/questions") // API RESTful hiérarchique
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@PathVariable Long skillId, @RequestBody Question question) {
        return new ResponseEntity<>(questionService.createQuestion(skillId, question), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Question>> getQuestionsBySkill(@PathVariable Long skillId) {
        return ResponseEntity.ok(questionService.getQuestionsBySkill(skillId));
    }
}