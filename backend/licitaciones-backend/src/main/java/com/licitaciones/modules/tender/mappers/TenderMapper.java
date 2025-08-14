package com.licitaciones.modules.tender.mappers;

import com.licitaciones.modules.tender.entities.Tender;
import com.licitaciones.modules.tender.dtos.TenderDto;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TenderMapper {

    public TenderDto toDto(Tender tender) {
        if (tender == null) return null;

        TenderDto dto = new TenderDto();
        dto.setId(tender.getId());
        dto.setExternalId(tender.getExternalId());
        dto.setTitle(tender.getTitle());
        dto.setEntity(tender.getEntity());
        dto.setAmount(tender.getAmount());
        dto.setDeadline(tender.getDeadline());
        dto.setCategory(tender.getCategory());
        dto.setCpv(tender.getCpv());
        dto.setStatus(tender.getStatus().name());
        dto.setDescription(tender.getDescription());

        return dto;
    }

    public Tender toEntity(TenderDto dto) {
        if (dto == null) return null;

        Tender tender = new Tender();
        tender.setId(dto.getId());
        tender.setExternalId(dto.getExternalId());
        tender.setTitle(dto.getTitle());
        tender.setEntity(dto.getEntity());
        tender.setAmount(dto.getAmount());
        tender.setDeadline(dto.getDeadline());
        tender.setCategory(dto.getCategory());
        tender.setCpv(dto.getCpv());

        if (dto.getStatus() != null) {
            tender.setStatus(Tender.TenderStatus.valueOf(dto.getStatus()));
        }

        tender.setDescription(dto.getDescription());

        return tender;
    }

    public List<TenderDto> toDtoList(List<Tender> tenders) {
        return tenders.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Tender> toEntityList(List<TenderDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}