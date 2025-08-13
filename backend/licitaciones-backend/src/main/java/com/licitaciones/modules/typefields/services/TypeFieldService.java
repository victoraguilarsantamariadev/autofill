package com.licitaciones.modules.typefields.services;

import com.licitaciones.modules.typefields.dtos.CreateTypeFieldDto;
import com.licitaciones.modules.typefields.dtos.TypeFieldResponseDto;
import com.licitaciones.modules.typefields.entities.TypeField;
import com.licitaciones.modules.typefields.repositories.TypeFieldRepository;
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
public class TypeFieldService {

    private final TypeFieldRepository typeFieldRepository;
    private final UserRepository userRepository;

    public List<TypeFieldResponseDto> getAllActiveTypeFields() {
        log.info("Fetching all active type fields");
        return typeFieldRepository.findByIsActiveTrueOrderByNameAsc()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public TypeFieldResponseDto getTypeFieldById(Long id) {
        log.info("Fetching type field by id: {}", id);
        TypeField typeField = typeFieldRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Type field not found with id: " + id
                ));
        return mapToResponseDto(typeField);
    }

    public TypeFieldResponseDto createTypeField(CreateTypeFieldDto createTypeFieldDto, String userEmail) {
        log.info("Creating new type field: {} by user: {}", createTypeFieldDto.getName(), userEmail);

        if (typeFieldRepository.existsByNameAndIsActiveTrue(createTypeFieldDto.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Type field with name '" + createTypeFieldDto.getName() + "' already exists"
            );
        }

        User creator = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found: " + userEmail
                ));

        TypeField typeField = new TypeField();
        typeField.setName(createTypeFieldDto.getName());
        typeField.setDescription(createTypeFieldDto.getDescription());
        typeField.setValidationRules(createTypeFieldDto.getValidationRules());
        typeField.setDefaultProperties(createTypeFieldDto.getDefaultProperties());
        typeField.setIsActive(createTypeFieldDto.getIsActive());
        typeField.setIcon(createTypeFieldDto.getIcon());
        typeField.setCategory(createTypeFieldDto.getCategory());
        typeField.setCreatedBy(creator);

        TypeField savedTypeField = typeFieldRepository.save(typeField);
        log.info("Type field created successfully with id: {}", savedTypeField.getId());

        return mapToResponseDto(savedTypeField);
    }

    public TypeFieldResponseDto updateTypeField(Long id, CreateTypeFieldDto updateTypeFieldDto, String userEmail) {
        log.info("Updating type field with id: {} by user: {}", id, userEmail);

        TypeField typeField = typeFieldRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Type field not found with id: " + id
                ));

        // Verificar si el nuevo nombre ya existe (excepto para el tipo actual)
        typeFieldRepository.findByNameAndIsActiveTrue(updateTypeFieldDto.getName())
                .ifPresent(existingTypeField -> {
                    if (!existingTypeField.getId().equals(id)) {
                        throw new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Type field with name '" + updateTypeFieldDto.getName() + "' already exists"
                        );
                    }
                });

        typeField.setName(updateTypeFieldDto.getName());
        typeField.setDescription(updateTypeFieldDto.getDescription());
        typeField.setValidationRules(updateTypeFieldDto.getValidationRules());
        typeField.setDefaultProperties(updateTypeFieldDto.getDefaultProperties());
        typeField.setIsActive(updateTypeFieldDto.getIsActive());
        typeField.setIcon(updateTypeFieldDto.getIcon());
        typeField.setCategory(updateTypeFieldDto.getCategory());

        TypeField updatedTypeField = typeFieldRepository.save(typeField);
        log.info("Type field updated successfully with id: {}", updatedTypeField.getId());

        return mapToResponseDto(updatedTypeField);
    }

    public void deleteTypeField(Long id) {
        log.info("Soft deleting type field with id: {}", id);

        TypeField typeField = typeFieldRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Type field not found with id: " + id
                ));

        typeField.setIsActive(false);
        typeFieldRepository.save(typeField);
        log.info("Type field soft deleted successfully with id: {}", id);
    }

    public List<TypeFieldResponseDto> searchTypeFields(String searchTerm) {
        log.info("Searching type fields with term: {}", searchTerm);
        return typeFieldRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(searchTerm)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    public List<TypeFieldResponseDto> getTypeFieldsByCategory(String category) {
        log.info("Fetching type fields by category: {}", category);
        return typeFieldRepository.findByCategoryAndIsActiveTrue(category)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    // Métodos para respuestas HTTP
    public Map<String, Object> getAllTypeFieldsResponse() {
        List<TypeFieldResponseDto> typeFields = getAllActiveTypeFields();

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Type fields retrieved successfully");
        response.put("data", typeFields);
        response.put("total", typeFields.size());

        return response;
    }

    public Map<String, Object> getTypeFieldByIdResponse(Long id) {
        TypeFieldResponseDto typeField = getTypeFieldById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Type field retrieved successfully");
        response.put("data", typeField);

        return response;
    }

    public Map<String, Object> createTypeFieldResponse(CreateTypeFieldDto createTypeFieldDto, String userEmail) {
        TypeFieldResponseDto createdTypeField = createTypeField(createTypeFieldDto, userEmail);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Type field created successfully");
        response.put("data", createdTypeField);

        return response;
    }

    public Map<String, Object> updateTypeFieldResponse(Long id, CreateTypeFieldDto updateTypeFieldDto, String userEmail) {
        TypeFieldResponseDto updatedTypeField = updateTypeField(id, updateTypeFieldDto, userEmail);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Type field updated successfully");
        response.put("data", updatedTypeField);

        return response;
    }

    public Map<String, Object> deleteTypeFieldResponse(Long id) {
        deleteTypeField(id);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Type field deleted successfully");

        return response;
    }

    public Map<String, Object> searchTypeFieldsResponse(String term) {
        List<TypeFieldResponseDto> typeFields = searchTypeFields(term);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Search completed successfully");
        response.put("data", typeFields);
        response.put("total", typeFields.size());
        response.put("searchTerm", term);

        return response;
    }

    public Map<String, Object> getTypeFieldsByCategoryResponse(String category) {
        List<TypeFieldResponseDto> typeFields = getTypeFieldsByCategory(category);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Type fields by category retrieved successfully");
        response.put("data", typeFields);
        response.put("total", typeFields.size());
        response.put("category", category);

        return response;
    }

    private TypeFieldResponseDto mapToResponseDto(TypeField typeField) {
        TypeFieldResponseDto dto = new TypeFieldResponseDto();
        dto.setId(typeField.getId());
        dto.setName(typeField.getName());
        dto.setDescription(typeField.getDescription());
        dto.setValidationRules(typeField.getValidationRules());
        dto.setDefaultProperties(typeField.getDefaultProperties());
        dto.setIsActive(typeField.getIsActive());
        dto.setIcon(typeField.getIcon());
        dto.setCategory(typeField.getCategory());
        dto.setCreatedByName(typeField.getCreatedBy() != null ? typeField.getCreatedBy().getName() : null);
        dto.setCreatedAt(typeField.getCreatedAt());
        dto.setUpdatedAt(typeField.getUpdatedAt());
        return dto;
    }
}