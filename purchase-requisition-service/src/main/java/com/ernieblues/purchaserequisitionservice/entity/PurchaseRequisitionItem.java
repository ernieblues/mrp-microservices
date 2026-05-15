package com.ernieblues.purchaserequisitionservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "purchase_requisition_items")
public class PurchaseRequisitionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer lineNumber;

    private Long productId;

    @Column(nullable = false)
    private String description;

    private String vendorPartNumber;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(nullable = false)
    private String unitOfMeasure = "EA";

    @Column(precision = 18, scale = 2, nullable = false)
    private BigDecimal unitPrice;

    @Transient
    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(quantity);
    }

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

    public PurchaseRequisitionItem() {}

    public PurchaseRequisitionItem(Integer lineNumber, String description, BigDecimal quantity,
                                   String unitOfMeasure, BigDecimal unitPrice) {
        this.lineNumber = lineNumber;
        this.description = description;
        this.quantity = quantity;
        this.unitOfMeasure = unitOfMeasure;
        this.unitPrice = unitPrice;
    }
}
