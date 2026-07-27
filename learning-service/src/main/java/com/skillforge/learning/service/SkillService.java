package com.skillforge.learning.service;

import com.skillforge.learning.entity.Skill;
import com.skillforge.learning.exception.SkillNotFoundException;
import com.skillforge.learning.repository.SkillRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
    
    public Skill getSkill(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException("Skill not found with id: " + id));
    }
}