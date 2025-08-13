package com.licitaciones.modules.typefields.controllers;

import com.licitaciones.modules.typefields.dtos.CreateTypeFieldDto;
import com.licitaciones.modules.typefields.services.TypeFieldService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/type-fields")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "http://localhost:4200")
public class TypeFieldController {

    private final TypeFieldService typeFieldService;
    // ✅ AGREGAR ESTE ENDPOINT DE PRUEBA
    @PostMapping("/test")
    public ResponseEntity<Map<String, Object>> createTypeFieldTest(
            @Valid @RequestBody CreateTypeFieldDto createTypeFieldDto) {
        // Usar un email fijo para testing
        String testUserEmail = "test.user@licitaciones.com";
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(typeFieldService.createTypeFieldResponse(createTypeFieldDto, testUserEmail));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllTypeFields() {
        return ResponseEntity.ok(typeFieldService.getAllTypeFieldsResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getTypeFieldById(@PathVariable Long id) {
        return ResponseEntity.ok(typeFieldService.getTypeFieldByIdResponse(id));
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createTypeField(
            @Valid @RequestBody CreateTypeFieldDto createTypeFieldDto,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(typeFieldService.createTypeFieldResponse(createTypeFieldDto, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateTypeField(
            @PathVariable Long id,
            @Valid @RequestBody CreateTypeFieldDto updateTypeFieldDto,
            Authentication authentication) {
        return ResponseEntity.ok(typeFieldService.updateTypeFieldResponse(id, updateTypeFieldDto, authentication.getName()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteTypeField(@PathVariable Long id) {
        return ResponseEntity.ok(typeFieldService.deleteTypeFieldResponse(id));
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchTypeFields(@RequestParam String term) {
        return ResponseEntity.ok(typeFieldService.searchTypeFieldsResponse(term));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Map<String, Object>> getTypeFieldsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(typeFieldService.getTypeFieldsByCategoryResponse(category));
    }
}