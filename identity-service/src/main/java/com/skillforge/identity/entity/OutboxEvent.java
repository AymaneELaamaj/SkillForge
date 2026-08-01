package com.skillforge.identity.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "outbox")
public class OutboxEvent {

    @Id
    private UUID id;

    @Column(name = "aggregatetype", nullable = false)
    private String aggregateType;

    @Column(name = "aggregateid", nullable = false)
    private String aggregateId;

    @Column(nullable = false)
    private String type;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(nullable = false, columnDefinition = "jsonb")
    private String payload;

    @Column(nullable = false)
    private Instant timestamp;

    protected OutboxEvent() {
    }

    public OutboxEvent(String aggregateType, String aggregateId, String type, String payload, Instant timestamp) {
        this.id = UUID.randomUUID();
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.type = type;
        this.payload = payload;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }
}
