package com.ernieblues.purchaserequisitionservice.dto;

import java.math.BigDecimal;

public record PurchaseRequisitionItemDto(
        Long id,
        Integer lineNumber,
        Long productId,
        String description,
        String vendorPartNumber,
        BigDecimal quantity,
        String unitOfMeasure,
        BigDecimal unitPrice,
        BigDecimal totalPrice
) { }
