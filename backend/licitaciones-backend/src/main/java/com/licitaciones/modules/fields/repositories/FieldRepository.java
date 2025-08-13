package com.licitaciones.modules.fields.repositories;

import com.licitaciones.modules.fields.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FieldRepository extends JpaRepository<Field, Long> {

    List<Field> findByIsActiveTrue();

    List<Field> findByIsActiveTrueOrderByNameAsc();

    Optional<Field> findByNameAndIsActiveTrue(String name);

    List<Field> findByFieldTypeAndIsActiveTrue(String fieldType);

    @Query("SELECT f FROM Field f WHERE f.createdBy.id = :userId AND f.isActive = true")
    List<Field> findByCreatedByIdAndIsActiveTrue(@Param("userId") Long userId);

    @Query("SELECT f FROM Field f WHERE f.name LIKE %:searchTerm% AND f.isActive = true")
    List<Field> findByNameContainingIgnoreCaseAndIsActiveTrue(@Param("searchTerm") String searchTerm);
}