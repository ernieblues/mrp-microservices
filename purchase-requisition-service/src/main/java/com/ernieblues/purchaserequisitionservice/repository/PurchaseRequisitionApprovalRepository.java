package com.ernieblues.purchaserequisitionservice.repository;

import com.ernieblues.purchaserequisitionservice.entity.PurchaseRequisitionApproval;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRequisitionApprovalRepository
        extends JpaRepository<PurchaseRequisitionApproval, Long> {

    List<PurchaseRequisitionApproval> findByPurchaseRequisition_Id(
            Long purchaseRequisition
    );

    Optional<PurchaseRequisitionApproval> findByIdAndPurchaseRequisition_Id(
            Long id,
            Long purchaseRequisition
    );
}
