package com.licitaciones.modules.tender.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.licitaciones.modules.tender.services.TenderService;
import com.licitaciones.modules.tender.dtos.TenderDto;
import com.licitaciones.modules.tender.dtos.DashboardStatsDto;
import java.util.List;

@RestController
@RequestMapping("/api/tenders")
@CrossOrigin(origins = "http://localhost:4200")
public class TenderController {

    @Autowired
    private TenderService tenderService;

    // Dashboard stats
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDto> getDashboardStats() {
        return ResponseEntity.ok(tenderService.getDashboardStats());
    }

    // Recent tenders para el dashboard
    @GetMapping("/recent")
    public ResponseEntity<List<TenderDto>> getRecentTenders() {
        return ResponseEntity.ok(tenderService.getRecentTenders());
    }

    // Todos los tenders
    @GetMapping
    public ResponseEntity<List<TenderDto>> getAllTenders() {
        return ResponseEntity.ok(tenderService.getAllTenders());
    }

    // Search tenders
    @GetMapping("/search")
    public ResponseEntity<List<TenderDto>> searchTenders(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String cpv,
            @RequestParam(required = false) String category
    ) {
        return ResponseEntity.ok(tenderService.searchTenders(query, cpv, category));
    }

    // Get tender by ID
    @GetMapping("/{id}")
    public ResponseEntity<TenderDto> getTender(@PathVariable String id) {
        return ResponseEntity.ok(tenderService.getTenderById(id));
    }

    // Test endpoint
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("✅ Tender Controller funcionando! 🎯");
    }
}