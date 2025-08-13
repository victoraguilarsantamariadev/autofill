package com.licitaciones.modules.fields.services;

import com.licitaciones.modules.fields.dtos.CreateFieldDto;
import com.licitaciones.modules.fields.dtos.FieldResponseDto;
import com.licitaciones.modules.fields.entities.Field;
import com.licitaciones.modules.fields.repositories.FieldRepository;
import com.licitaciones.modules.user.entities.User;
import com.licitaciones.modules.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class FieldService {

    private final FieldRepository fieldRepository;
    private final UserRepository userRepository;

    // Métodos internos (sin response)
    public List<FieldResponseDto> getAllActiveFields() {
        log.info("Fetching all active fields");
        return fieldRepository.findByIsActiveTrueOrderByNameAsc()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public FieldResponseDto getFieldById(Long id) {
        log.info("Fetching field by id: {}", id);
        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Field not found with id: " + id
                ));
        return mapToResponseDto(field);
    }

    public FieldResponseDto createField(CreateFieldDto createFieldDto, String userEmail) {
        log.info("Creating new field: {} by user: {}", createFieldDto.getName(), userEmail);

        // Verificar si ya existe un campo con ese nombre
        if (fieldRepository.findByNameAndIsActiveTrue(createFieldDto.getName()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Field with name '" + createFieldDto.getName() + "' already exists"
            );
        }

        User creator = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found: " + userEmail
                ));

        Field field = new Field();
        field.setName(createFieldDto.getName());
        field.setDescription(createFieldDto.getDescription());
        field.setFieldType(createFieldDto.getFieldType());
        field.setDefaultValue(createFieldDto.getDefaultValue());
        field.setIsRequired(createFieldDto.getIsRequired());
        field.setIsActive(createFieldDto.getIsActive());
        field.setCreatedBy(creator);

        Field savedField = fieldRepository.save(field);
        log.info("Field created successfully with id: {}", savedField.getId());

        return mapToResponseDto(savedField);
    }

    public FieldResponseDto updateField(Long id, CreateFieldDto updateFieldDto, String userEmail) {
        log.info("Updating field with id: {} by user: {}", id, userEmail);

        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Field not found with id: " + id
                ));

        // Verificar si el nuevo nombre ya existe (excepto para el campo actual)
        fieldRepository.findByNameAndIsActiveTrue(updateFieldDto.getName())
                .ifPresent(existingField -> {
                    if (!existingField.getId().equals(id)) {
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Field with name '" + updateFieldDto.getName() + "' already exists"
                        );
                    }
                });

        field.setName(updateFieldDto.getName());
        field.setDescription(updateFieldDto.getDescription());
        field.setFieldType(updateFieldDto.getFieldType());
        field.setDefaultValue(updateFieldDto.getDefaultValue());
        field.setIsRequired(updateFieldDto.getIsRequired());
        field.setIsActive(updateFieldDto.getIsActive());

        Field updatedField = fieldRepository.save(field);
        log.info("Field updated successfully with id: {}", updatedField.getId());

        return mapToResponseDto(updatedField);
    }

    public void deleteField(Long id) {
        log.info("Soft deleting field with id: {}", id);

        Field field = fieldRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Field not found with id: " + id
                ));

        field.setIsActive(false);
        fieldRepository.save(field);
        log.info("Field soft deleted successfully with id: {}", id);
    }

    public List<FieldResponseDto> searchFields(String searchTerm) {
        log.info("Searching fields with term: {}", searchTerm);
        return fieldRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(searchTerm)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public List<FieldResponseDto> getFieldsByType(String fieldType) {
        log.info("Fetching fields by type: {}", fieldType);
        return fieldRepository.findByFieldTypeAndIsActiveTrue(fieldType)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // Métodos para respuestas HTTP (con response wrapping)
    public Map<String, Object> getAllFieldsResponse() {
        List<FieldResponseDto> fields = getAllActiveFields();

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Fields retrieved successfully");
        response.put("data", fields);
        response.put("total", fields.size());

        return response;
    }

    public Map<String, Object> getFieldByIdResponse(Long id) {
        FieldResponseDto field = getFieldById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Field retrieved successfully");
        response.put("data", field);

        return response;
    }

    public Map<String, Object> createFieldResponse(CreateFieldDto createFieldDto, String userEmail) {
        FieldResponseDto createdField = createField(createFieldDto, userEmail);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Field created successfully");
        response.put("data", createdField);

        return response;
    }

    public Map<String, Object> updateFieldResponse(Long id, CreateFieldDto updateFieldDto, String userEmail) {
        FieldResponseDto updatedField = updateField(id, updateFieldDto, userEmail);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Field updated successfully");
        response.put("data", updatedField);

        return response;
    }

    public Map<String, Object> deleteFieldResponse(Long id) {
        deleteField(id);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Field deleted successfully");

        return response;
    }

    public Map<String, Object> searchFieldsResponse(String term) {
        List<FieldResponseDto> fields = searchFields(term);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Search completed successfully");
        response.put("data", fields);
        response.put("total", fields.size());
        response.put("searchTerm", term);

        return response;
    }

    public Map<String, Object> getFieldsByTypeResponse(String fieldType) {
        List<FieldResponseDto> fields = getFieldsByType(fieldType);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Fields by type retrieved successfully");
        response.put("data", fields);
        response.put("total", fields.size());
        response.put("fieldType", fieldType);

        return response;
    }

    private FieldResponseDto mapToResponseDto(Field field) {
        FieldResponseDto dto = new FieldResponseDto();
        dto.setId(field.getId());
        dto.setName(field.getName());
        dto.setDescription(field.getDescription());
        dto.setFieldType(field.getFieldType());
        dto.setDefaultValue(field.getDefaultValue());
        dto.setIsRequired(field.getIsRequired());
        dto.setIsActive(field.getIsActive());
        dto.setCreatedByName(field.getCreatedBy() != null ? field.getCreatedBy().getName() : null);
        dto.setCreatedAt(field.getCreatedAt());
        dto.setUpdatedAt(field.getUpdatedAt());
        return dto;
    }
}