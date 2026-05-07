package com.ernieblues.userservice.dto;

public record UserDto(
        Long id,
        String email,
        String firstName,
        String lastName,
        String phone,
        String addressLine1,
        String addressLine2,
        String city,
        String state,
        String postalCode,
        String country,
        String displayName
) { }
