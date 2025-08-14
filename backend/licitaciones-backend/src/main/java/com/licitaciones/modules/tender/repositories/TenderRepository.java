package com.licitaciones.modules.tender.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.licitaciones.modules.tender.entities.Tender;
import java.util.List;
import java.util.Optional;

@Repository
public interface TenderRepository extends JpaRepository<Tender, Long> {

    // Find by external ID
    Optional<Tender> findByExternalId(String externalId);

    // Count by status
    long countByStatus(Tender.TenderStatus status);

    // Recent tenders
    List<Tender> findTop10ByOrderByCreatedAtDesc();

    // Search methods
    List<Tender> findByTitleContainingIgnoreCase(String title);
    List<Tender> findByCategory(String category);
    List<Tender> findByCpv(String cpv);
    List<Tender> findByEntity(String entity);

    // Active tenders
    List<Tender> findByStatus(Tender.TenderStatus status);
}