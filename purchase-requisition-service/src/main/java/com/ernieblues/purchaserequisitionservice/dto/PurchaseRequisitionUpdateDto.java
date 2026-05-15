package com.ernieblues.purchaserequisitionservice.dto;

import java.time.OffsetDateTime;

public record PurchaseRequisitionUpdateDto(
        OffsetDateTime dateRequired,
        Long costCenterId,
        Long vendorId,
        String comments
) { }
