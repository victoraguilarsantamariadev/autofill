package com.licitaciones.modules.typefields.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateTypeFieldDto {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    private String validationRules;

    private String defaultProperties;

    private Boolean isActive = true;

    private String icon;

    private String category = "basic";
}