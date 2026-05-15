package com.ernieblues.purchaserequisitionservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "purchase_requisition_approvals")
public class PurchaseRequisitionApproval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long reviewerId;

    private OffsetDateTime dateReviewed;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_requisition_id", nullable = false)
    private PurchaseRequisition purchaseRequisition;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    public PurchaseRequisitionApproval() {}

    public PurchaseRequisitionApproval(Long approvedById, ApprovalStatus approvalStatus,
                                       OffsetDateTime dateApproved, String comments) {
        this.reviewerId = approvedById;
        this.approvalStatus = approvalStatus;
        this.dateReviewed = dateApproved;
        this.comments = comments;
    }
}
