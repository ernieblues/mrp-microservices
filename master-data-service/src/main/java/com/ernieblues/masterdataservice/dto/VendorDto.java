package com.ernieblues.masterdataservice.dto;

public record VendorDto(
    Long id,
    String code,
    String name,
    String contactName,
    String email,
    String phone,
    String addressLine1,
    String addressLine2,
    String city,
    String state,
    String postalCode,
    String country,
    boolean active
) { }
