package com.licitaciones.modules.fields.controllers;

import com.licitaciones.modules.fields.dtos.CreateFieldDto;
import com.licitaciones.modules.fields.dtos.FieldResponseDto;
import com.licitaciones.modules.fields.services.FieldService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class FieldController {

    private final FieldService fieldService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllFields() {
        return ResponseEntity.ok(fieldService.getAllFieldsResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getFieldById(@PathVariable Long id) {
        return ResponseEntity.ok(fieldService.getFieldByIdResponse(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createField(
            @Valid @RequestBody CreateFieldDto createFieldDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(fieldService.createFieldResponse(createFieldDto, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateField(
            @PathVariable Long id,
            @Valid @RequestBody CreateFieldDto updateFieldDto,
            Authentication authentication) {
        return ResponseEntity.ok(fieldService.updateFieldResponse(id, updateFieldDto, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteField(@PathVariable Long id) {
        return ResponseEntity.ok(fieldService.deleteFieldResponse(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchFields(@RequestParam String term) {
        return ResponseEntity.ok(fieldService.searchFieldsResponse(term));
    }

    @GetMapping("/type/{fieldType}")
    public ResponseEntity<Map<String, Object>> getFieldsByType(@PathVariable String fieldType) {
        return ResponseEntity.ok(fieldService.getFieldsByTypeResponse(fieldType));
    }
}