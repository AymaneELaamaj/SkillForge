package com.skillforge.learning.service;

import com.skillforge.learning.client.IdentityServiceClient;
import com.skillforge.learning.dto.request.CreateAssessmentRequest;
import com.skillforge.learning.entity.Assessment;
import com.skillforge.learning.enums.AssessmentStatus;
import com.skillforge.learning.exception.AssessmentNotFoundException;
import com.skillforge.learning.repository.AssessmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AssessmentService {

    private final SkillService skillService;
    private final IdentityServiceClient identityServiceClient;
    private final AssessmentRepository assessmentRepository;

    public AssessmentService(SkillService skillService,
                             IdentityServiceClient identityServiceClient,
                             AssessmentRepository assessmentRepository) {
        this.skillService = skillService;
        this.identityServiceClient = identityServiceClient;
        this.assessmentRepository = assessmentRepository;
    }
        
        public Assessment createAssessment(CreateAssessmentRequest request) {
            skillService.getSkill(request.skillId());

            identityServiceClient.getUserById(request.userId());

            return assessmentRepository
                    .findByUserIdAndSkillIdAndStatus(request.userId(), request.skillId(), AssessmentStatus.IN_PROGRESS)
                    .orElseGet(() -> {
                        Assessment assessment = new Assessment(
                                request.userId(),
                                request.skillId(),
                                LocalDateTime.now(),
                                AssessmentStatus.IN_PROGRESS
                        );
                        return assessmentRepository.save(assessment);
                    });
        }
        public Assessment getAssessment(Long id) {
        return assessmentRepository.findById(id)
                .orElseThrow(() -> new AssessmentNotFoundException("Évaluation introuvable avec l'ID: " + id));
    }
}
