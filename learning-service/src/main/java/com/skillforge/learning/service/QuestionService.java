package com.skillforge.learning.service;

import com.skillforge.learning.entity.Question;
import com.skillforge.learning.entity.Skill;
import com.skillforge.learning.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final SkillService skillService; // On injecte le service, pas le repository

    public QuestionService(QuestionRepository questionRepository, SkillService skillService) {
        this.questionRepository = questionRepository;
        this.skillService = skillService;
    }

    public Question createQuestion(Long skillId, Question question) {
        // On s'assure que la compétence existe avant de lier la question
        Skill skill = skillService.getSkill(skillId);
        question.setSkill(skill);
        return questionRepository.save(question);
    }

    public List<Question> getQuestionsBySkill(Long skillId) {
        return questionRepository.findBySkillId(skillId);
    }
}