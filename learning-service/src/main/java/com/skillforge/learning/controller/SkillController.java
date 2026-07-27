package com.skillforge.learning.controller;

import com.skillforge.learning.dto.request.CreateSkillRequest;
import com.skillforge.learning.dto.response.SkillResponse;
import com.skillforge.learning.entity.Skill;
import com.skillforge.learning.mapper.SkillMapper;
import com.skillforge.learning.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/learning/skills")
public class SkillController {

    private final SkillService skillService;
    private final SkillMapper skillMapper;

    public SkillController(SkillService skillService, SkillMapper skillMapper) {
        this.skillService = skillService;
        this.skillMapper = skillMapper;
    }

    @PostMapping
    public ResponseEntity<SkillResponse> createSkill(@Valid @RequestBody CreateSkillRequest request) {
        Skill skill = skillMapper.toEntity(request);
        Skill savedSkill = skillService.createSkill(skill);
        return new ResponseEntity<>(skillMapper.toResponse(savedSkill), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAllSkills() {
        List<SkillResponse> responses = skillService.getAllSkills().stream()
                .map(skillMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getSkill(@PathVariable Long id) {
        Skill skill = skillService.getSkill(id);
        return ResponseEntity.ok(skillMapper.toResponse(skill));
    }
}