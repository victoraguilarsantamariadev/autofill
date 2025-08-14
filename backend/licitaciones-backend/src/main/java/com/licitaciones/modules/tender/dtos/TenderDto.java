package com.licitaciones.modules.tender.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class TenderDto {
    private Long id;
    private String externalId;
    private String title;
    private String entity;
    private String amount;

    @JsonFormat(pattern = "dd MMM yyyy")
    private LocalDateTime deadline;

    private String category;
    private String cpv;
    private String status;
    private String description;
    private Double relevanceScore; // Para el filtrado inteligente

    // Constructors
    public TenderDto() {}

    public TenderDto(Long id, String externalId, String title, String entity,
                     String amount, LocalDateTime deadline, String category,
                     String cpv, String status) {
        this.id = id;
        this.externalId = externalId;
        this.title = title;
        this.entity = entity;
        this.amount = amount;
        this.deadline = deadline;
        this.category = category;
        this.cpv = cpv;
        this.status = status;
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

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getRelevanceScore() { return relevanceScore; }
    public void setRelevanceScore(Double relevanceScore) { this.relevanceScore = relevanceScore; }
}