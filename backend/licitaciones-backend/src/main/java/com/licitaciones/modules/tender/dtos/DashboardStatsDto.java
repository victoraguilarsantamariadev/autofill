package com.licitaciones.modules.tender.dtos;

public class DashboardStatsDto {
    private Long tenders;
    private Long pdfsAnalyzed;
    private Long aiAnalysis;
    private String totalValue;

    public DashboardStatsDto() {}

    public DashboardStatsDto(Long tenders, Long pdfsAnalyzed, Long aiAnalysis, String totalValue) {
        this.tenders = tenders;
        this.pdfsAnalyzed = pdfsAnalyzed;
        this.aiAnalysis = aiAnalysis;
        this.totalValue = totalValue;
    }

    // Getters and Setters
    public Long getTenders() { return tenders; }
    public void setTenders(Long tenders) { this.tenders = tenders; }

    public Long getPdfsAnalyzed() { return pdfsAnalyzed; }
    public void setPdfsAnalyzed(Long pdfsAnalyzed) { this.pdfsAnalyzed = pdfsAnalyzed; }

    public Long getAiAnalysis() { return aiAnalysis; }
    public void setAiAnalysis(Long aiAnalysis) { this.aiAnalysis = aiAnalysis; }

    public String getTotalValue() { return totalValue; }
    public void setTotalValue(String totalValue) { this.totalValue = totalValue; }
}