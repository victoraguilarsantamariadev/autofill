package com.licitaciones.modules.fields.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FieldResponseDto {
    private Long id;
    private String name;
    private String description;
    private String fieldType;
    private String defaultValue;
    private Boolean isRequired;
    private Boolean isActive;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}