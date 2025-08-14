package com.licitaciones.modules.tender.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tenders")
public class Tender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id", unique = true)
    private String externalId;

    @Column(name = "title", nullable = false, length = 1000)
    private String title;

    @Column(name = "entity", nullable = false)
    private String entity;

    @Column(name = "amount")
    private String amount;

    @Column(name = "deadline")
    private LocalDateTime deadline;

    @Column(name = "category")
    private String category;

    @Column(name = "cpv")
    private String cpv;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TenderStatus status = TenderStatus.ACTIVE;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    // Constructors
    public Tender() {}

    public Tender(String externalId, String title, String entity, String amount,
                  LocalDateTime deadline, String category, String cpv) {
        this.externalId = externalId;
        this.title = title;
        this.entity = entity;
        this.amount = amount;
        this.deadline = deadline;
        this.category = category;
        this.cpv = cpv;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExternalId() { return externalId; }
    public void setExternalId(String externalId) { this.externalId = externalId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getEntity() { return entity; }
    public void setEntity(String entity) { this.entity = entity; }

    public String getAmount() { return amount; }
    public void setAmount(String amount) { this.amount = amount; }

    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCpv() { return cpv; }
    public void setCpv(String cpv) { this.cpv = cpv; }

    public TenderStatus getStatus() { return status; }
    public void setStatus(TenderStatus status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    // Enum for status
    public enum TenderStatus {
        ACTIVE, CLOSING, CLOSED
    }
}