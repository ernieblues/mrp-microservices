package com.ernieblues.purchaserequisitionservice.dto;

public record CostCenterDto(
        Long id,
        String code,
        String name,
        String description,
        boolean active
) { }
