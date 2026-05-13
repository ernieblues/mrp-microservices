package com.ernieblues.masterdataservice.dto;

public record CostCenterDto(
    Long id,
    String code,
    String name,
    String description,
    boolean active
) { }
