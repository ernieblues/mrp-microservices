package com.ernieblues.purchaserequisitionservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "purchase_requisitions")
public class PurchaseRequisition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, insertable = false, updatable = false)
    private Long purchaseRequisitionNumber;

    @Column(nullable = false)
    private OffsetDateTime dateRequested;

    @Column(nullable = false)
    private OffsetDateTime dateRequired;

    @Column(nullable = false)
    private Long requestedById;

    @Column(nullable = false)
    private Long costCenterId;

    @Column(nullable = false)
    private Long vendorId;

    private String comments;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequisitionStatus status = RequisitionStatus.PENDING;

    @OneToMany(mappedBy = "purchaseRequisition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseRequisitionItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "purchaseRequisition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseRequisitionApproval> approvals = new ArrayList<>();

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    public PurchaseRequisition() {
    }

    public PurchaseRequisition(
            Long requestedById,
            OffsetDateTime dateRequested,
            OffsetDateTime dateRequired,
            Long costCenterId,
            Long vendorId,
            String comments) {

        this.requestedById = requestedById;
        this.dateRequested = dateRequested;
        this.dateRequired = dateRequired;
        this.costCenterId = costCenterId;
        this.vendorId = vendorId;
        this.comments = comments;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }

    @Transient
    public BigDecimal getTotalCost() {
        return items.stream()
                .map(PurchaseRequisitionItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void calculateStatus() {
        if (approvals == null || approvals.isEmpty()) {
            setStatus(RequisitionStatus.PENDING);
            return;
        }

        boolean hasRejectedApproval = approvals.stream()
                .anyMatch(a -> a.getApprovalStatus() == ApprovalStatus.REJECTED);

        if (hasRejectedApproval) {
            setStatus(RequisitionStatus.REJECTED);
            return;
        }

        boolean allApproved = approvals.stream()
                .allMatch(a -> a.getApprovalStatus() == ApprovalStatus.APPROVED);

        if (allApproved) {
            setStatus(RequisitionStatus.APPROVED);
            return;
        }

        setStatus(RequisitionStatus.PENDING);
    }
}
