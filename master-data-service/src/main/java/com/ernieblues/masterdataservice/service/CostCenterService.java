package com.ernieblues.masterdataservice.service;

import com.ernieblues.masterdataservice.dto.CostCenterDto;
import com.ernieblues.masterdataservice.dto.CostCenterLookupDto;
import com.ernieblues.masterdataservice.entity.CostCenter;
import com.ernieblues.masterdataservice.repository.CostCenterRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CostCenterService
{
    private final CostCenterRepository costCenterRepository;

    // --------------------------------------------------
    //                       CREATE
    // --------------------------------------------------

    public CostCenterDto create(CostCenterDto dto)
    {
        CostCenter costCenter = new CostCenter();

        costCenter.setCode(dto.code());
        costCenter.setName(dto.name());
        costCenter.setDescription(dto.description());
        costCenter.setActive(dto.active());

        CostCenter saved = costCenterRepository.save(costCenter);

        return mapToDto(saved);
    }

    // --------------------------------------------------
    //                        READ
    // --------------------------------------------------

    public List<CostCenterDto> getAll()
    {
        return costCenterRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public CostCenterDto getById(Integer id)
    {
        CostCenter costCenter = costCenterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Cost center not found: " + id));

        return mapToDto(costCenter);
    }

    // --------------------------------------------------
    //                       UPDATE
    // --------------------------------------------------

    public List<CostCenterLookupDto> lookup()
    {
        return costCenterRepository.findAll()
                .stream()
                .filter(CostCenter::isActive)
                .sorted((a, b) ->
                {
                    String codeA = a.getCode() == null ? "" : a.getCode();
                    String codeB = b.getCode() == null ? "" : b.getCode();

                    return codeA.compareToIgnoreCase(codeB);
                })
                .map(costCenter ->
                {
                    String code = costCenter.getCode();
                    String name = costCenter.getName();

                    String display = code == null || code.isBlank()
                            ? name
                            : code + " - " + name;

                    return new CostCenterLookupDto(
                            costCenter.getId(),
                            display
                    );
                })
                .toList();
    }

    public void update(Integer id, CostCenterDto dto)
    {
        CostCenter costCenter = costCenterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Cost center not found: " + id));

        costCenter.setCode(dto.code());
        costCenter.setName(dto.name());
        costCenter.setDescription(dto.description());
        costCenter.setActive(dto.active());

        costCenterRepository.save(costCenter);
    }

    // --------------------------------------------------
    //                       DELETE
    // --------------------------------------------------

    public void delete(Integer id)
    {
        CostCenter costCenter = costCenterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Cost center not found: " + id));

        costCenterRepository.delete(costCenter);
    }

    // --------------------------------------------------
    //                      HELPERS
    // --------------------------------------------------

    private CostCenterDto mapToDto(CostCenter costCenter) {
        return new CostCenterDto(
                costCenter.getId(),
                costCenter.getCode(),
                costCenter.getName(),
                costCenter.getDescription(),
                costCenter.isActive()
        );
    }
}
