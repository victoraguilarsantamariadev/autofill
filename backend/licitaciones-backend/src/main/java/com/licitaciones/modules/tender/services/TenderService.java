package com.licitaciones.modules.tender.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.licitaciones.modules.tender.entities.Tender;
import com.licitaciones.modules.tender.repositories.TenderRepository;
import com.licitaciones.modules.tender.dtos.TenderDto;
import com.licitaciones.modules.tender.dtos.DashboardStatsDto;
import com.licitaciones.modules.tender.mappers.TenderMapper;
import java.util.*;
import java.time.LocalDateTime;

@Service
public class TenderService {

    @Autowired
    private TenderRepository tenderRepository;

    @Autowired
    private TenderMapper tenderMapper;

    public DashboardStatsDto getDashboardStats() {
        // Datos reales de la base de datos
        long totalTenders = tenderRepository.count();
        long activeTenders = tenderRepository.countByStatus(Tender.TenderStatus.ACTIVE);

        return new DashboardStatsDto(
                activeTenders,
                23L, // TODO: Implementar cuando tengas el módulo de files
                45L, // TODO: Implementar cuando tengas el módulo de IA
                "€2.3M" // TODO: Calcular valor real
        );
    }

    public List<TenderDto> getRecentTenders() {
        // Si no hay datos reales, crear datos de prueba
        List<Tender> tenders = tenderRepository.findTop10ByOrderByCreatedAtDesc();

        if (tenders.isEmpty()) {
            tenders = createSampleTenders();
        }

        return tenderMapper.toDtoList(tenders);
    }

    public List<TenderDto> getAllTenders() {
        List<Tender> tenders = tenderRepository.findAll();
        return tenderMapper.toDtoList(tenders);
    }

    public List<TenderDto> searchTenders(String query, String cpv, String category) {
        List<Tender> tenders;

        if (query != null && !query.trim().isEmpty()) {
            tenders = tenderRepository.findByTitleContainingIgnoreCase(query);
        } else if (category != null && !category.trim().isEmpty()) {
            tenders = tenderRepository.findByCategory(category);
        } else if (cpv != null && !cpv.trim().isEmpty()) {
            tenders = tenderRepository.findByCpv(cpv);
        } else {
            tenders = tenderRepository.findAll();
        }

        return tenderMapper.toDtoList(tenders);
    }

    public TenderDto getTenderById(String id) {
        Tender tender = tenderRepository.findByExternalId(id)
                .orElseThrow(() -> new RuntimeException("Tender not found: " + id));
        return tenderMapper.toDto(tender);
    }

    // Crear datos de prueba si no hay datos reales
    private List<Tender> createSampleTenders() {
        List<Tender> sampleTenders = new ArrayList<>();

        Tender t1 = new Tender(
                "2024-001",
                "Suministro de equipos informáticos para administración local",
                "Ayuntamiento de Madrid",
                "€450,000",
                LocalDateTime.of(2024, 2, 15, 23, 59),
                "Tecnología",
                "30200000-1"
        );
        t1.setStatus(Tender.TenderStatus.ACTIVE);

        Tender t2 = new Tender(
                "2024-002",
                "Servicios de consultoría en transformación digital",
                "Generalitat de Catalunya",
                "€280,000",
                LocalDateTime.of(2024, 2, 22, 23, 59),
                "Servicios",
                "72000000-5"
        );
        t2.setStatus(Tender.TenderStatus.CLOSING);

        Tender t3 = new Tender(
                "2024-003",
                "Obras de rehabilitación de infraestructuras públicas",
                "Junta de Andalucía",
                "€1,200,000",
                LocalDateTime.of(2024, 2, 28, 23, 59),
                "Obras",
                "45000000-7"
        );
        t3.setStatus(Tender.TenderStatus.ACTIVE);

        Tender t4 = new Tender(
                "2024-004",
                "Mantenimiento de sistemas informáticos",
                "Diputación de Barcelona",
                "€150,000",
                LocalDateTime.of(2024, 3, 10, 23, 59),
                "Mantenimiento",
                "72000000-5"
        );
        t4.setStatus(Tender.TenderStatus.ACTIVE);

        sampleTenders.add(t1);
        sampleTenders.add(t2);
        sampleTenders.add(t3);
        sampleTenders.add(t4);

        return sampleTenders;
    }
}