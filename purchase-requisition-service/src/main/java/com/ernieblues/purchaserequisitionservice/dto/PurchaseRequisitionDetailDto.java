package com.ernieblues.purchaserequisitionservice.dto;

import com.ernieblues.purchaserequisitionservice.entity.RequisitionStatus;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public record PurchaseRequisitionDetailDto(
        Long id,
        Long purchaseRequisitionNumber,
        OffsetDateTime dateRequested,
        OffsetDateTime dateRequired,
        String comments,
        BigDecimal totalCost,
        RequisitionStatus status,
        List<PurchaseRequisitionItemDto> items,
        List<PurchaseRequisitionApprovalDto> approvals
) { }
