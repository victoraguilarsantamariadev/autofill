package com.licitaciones.modules.typefields.repositories;

import com.licitaciones.modules.typefields.entities.TypeField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeFieldRepository extends JpaRepository<TypeField, Long> {

    List<TypeField> findByIsActiveTrueOrderByNameAsc();

    Optional<TypeField> findByNameAndIsActiveTrue(String name);

    List<TypeField> findByCategoryAndIsActiveTrue(String category);

    @Query("SELECT tf FROM TypeField tf WHERE tf.name LIKE %:searchTerm% AND tf.isActive = true")
    List<TypeField> findByNameContainingIgnoreCaseAndIsActiveTrue(@Param("searchTerm") String searchTerm);

    boolean existsByNameAndIsActiveTrue(String name);
}