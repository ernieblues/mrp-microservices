package com.ernieblues.purchaserequisitionservice.dto;

import com.ernieblues.purchaserequisitionservice.entity.ApprovalStatus;

import java.time.OffsetDateTime;

public record PurchaseRequisitionApprovalDto(
        Long id,
        Long reviewerId,
        OffsetDateTime dateReviewed,
        ApprovalStatus approvalStatus,
        String comments
) { }
