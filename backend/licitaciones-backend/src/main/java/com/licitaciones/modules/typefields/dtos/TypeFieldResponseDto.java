package com.licitaciones.modules.typefields.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TypeFieldResponseDto {
    private Long id;
    private String name;
    private String description;
    private String validationRules;
    private String defaultProperties;
    private Boolean isActive;
    private String icon;
    private String category;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}