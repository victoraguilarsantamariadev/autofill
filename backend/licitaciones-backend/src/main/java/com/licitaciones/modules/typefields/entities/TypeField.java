package com.licitaciones.modules.typefields.entities;

import com.licitaciones.modules.user.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "type_fields")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // text, number, date, select, textarea, file, etc.

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "validation_rules", columnDefinition = "JSON")
    private String validationRules; // JSON con reglas de validación

    @Column(name = "default_properties", columnDefinition = "JSON")
    private String defaultProperties; // Propiedades por defecto del tipo

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "icon")
    private String icon; // Icono para mostrar en UI

    @Column(name = "category")
    private String category; // basic, advanced, custom

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}