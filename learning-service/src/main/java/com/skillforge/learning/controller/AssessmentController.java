package com.skillforge.learning.controller;

import com.skillforge.learning.dto.CreateAssessmentRequest;
import com.skillforge.learning.entity.Assessment;
import com.skillforge.learning.service.AssessmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/learning/assessments")
public class AssessmentController {

    private final AssessmentService assessmentService;

    public AssessmentController(AssessmentService assessmentService) {
        this.assessmentService = assessmentService;
    }

    @PostMapping
    public ResponseEntity<Assessment> createAssessment(@RequestBody CreateAssessmentRequest request) {
        return ResponseEntity.ok(assessmentService.createAssessment(request));
    }
}
