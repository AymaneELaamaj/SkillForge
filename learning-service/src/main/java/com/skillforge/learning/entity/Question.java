package com.skillforge.learning.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "questions")
@Data
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    // Plusieurs questions appartiennent à une seule compétence
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    public Question() {}

    public Question(String text, Skill skill) {
        this.text = text;
        this.skill = skill;
    }


}