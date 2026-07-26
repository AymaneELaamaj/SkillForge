package com.skillforge.learning.repository;

import com.skillforge.learning.entity.Assessment;
import com.skillforge.learning.enums.AssessmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {

    Optional<Assessment> findByUserIdAndSkillIdAndStatus(Long userId, Long skillId, AssessmentStatus status);
}
