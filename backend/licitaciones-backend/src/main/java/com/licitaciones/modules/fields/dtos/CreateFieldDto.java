package com.licitaciones.modules.fields.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateFieldDto {

    @NotBlank(message = "Name is required")
    private String name;

    private String description;

    @NotBlank(message = "Field type is required")
    private String fieldType;

    private String defaultValue;

    @NotNull(message = "Is required field must be specified")
    private Boolean isRequired = false;

    private Boolean isActive = true;
}