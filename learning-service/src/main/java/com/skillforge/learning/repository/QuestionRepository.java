package com.skillforge.learning.repository;

import com.skillforge.learning.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Méthode personnalisée générée automatiquement par Spring Data
    List<Question> findBySkillId(Long skillId);
}