package com.skillforge.learning.entity;

import com.skillforge.learning.enums.AssessmentStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "assessments")
@Data
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long skillId;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssessmentStatus status;

    public Assessment() {}

    public Assessment(Long userId, Long skillId, LocalDateTime startedAt, AssessmentStatus status) {
        this.userId = userId;
        this.skillId = skillId;
        this.startedAt = startedAt;
        this.status = status;
    }
}
