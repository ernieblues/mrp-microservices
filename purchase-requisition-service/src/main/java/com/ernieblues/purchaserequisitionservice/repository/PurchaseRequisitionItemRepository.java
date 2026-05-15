package com.ernieblues.purchaserequisitionservice.repository;

import com.ernieblues.purchaserequisitionservice.entity.PurchaseRequisitionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRequisitionItemRepository
        extends JpaRepository<PurchaseRequisitionItem, Long> {

    List<PurchaseRequisitionItem>
    findByPurchaseRequisition_IdOrderByLineNumberAscIdAsc(Long requisitionId);

    Optional<PurchaseRequisitionItem>
    findByIdAndPurchaseRequisition_Id(Long id, Long requisitionId);
}
