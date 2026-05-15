package com.ernieblues.purchaserequisitionservice.dto;

import com.ernieblues.purchaserequisitionservice.entity.RequisitionStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record PurchaseRequisitionDto(
        Long id,
        Long purchaseRequisitionNumber,
        OffsetDateTime dateRequested,
        OffsetDateTime dateRequired,
        Long requestedById,
        Long costCenterId,
        Long vendorId,
        String comments,
        BigDecimal totalCost,
        RequisitionStatus status,
        List<PurchaseRequisitionItemDto> items,
        List<PurchaseRequisitionApprovalDto> approvals
) { }
