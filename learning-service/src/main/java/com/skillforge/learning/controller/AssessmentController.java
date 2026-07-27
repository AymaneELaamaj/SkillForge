package com.skillforge.learning.controller;

import com.skillforge.learning.dto.request.CreateAssessmentRequest;
import com.skillforge.learning.dto.response.AssessmentResponse;
import com.skillforge.learning.entity.Assessment;
import com.skillforge.learning.mapper.AssessmentMapper;
import com.skillforge.learning.service.AssessmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/learning/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;
    private final AssessmentMapper assessmentMapper;

    public AssessmentController(AssessmentService assessmentService, AssessmentMapper assessmentMapper) {
        this.assessmentService = assessmentService;
        this.assessmentMapper = assessmentMapper;
    }

    @PostMapping
    public ResponseEntity<AssessmentResponse> createAssessment(@Valid @RequestBody CreateAssessmentRequest request) {
        // L'entité n'a que le userId. Le Service va la peupler avec le Skill complet.
        Assessment savedAssessment = assessmentService.createAssessment(request);
        
        return new ResponseEntity<>(assessmentMapper.toResponse(savedAssessment), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssessmentResponse> getAssessment(@PathVariable Long id) {
        Assessment assessment = assessmentService.getAssessment(id);
        return ResponseEntity.ok(assessmentMapper.toResponse(assessment));
    }
}