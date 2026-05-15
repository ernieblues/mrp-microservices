package com.ernieblues.purchaserequisitionservice.dto;

import java.time.OffsetDateTime;
import java.util.List;

public record PurchaseRequisitionCreateDto(
        OffsetDateTime dateRequired,
        Long costCenterId,
        Long vendorId,
        String comments,
        List<PurchaseRequisitionItemCreateDto> items
) { }
