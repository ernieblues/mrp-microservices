package com.ernieblues.purchaserequisitionservice.controller;

import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionApprovalDto;
import com.ernieblues.purchaserequisitionservice.service.PurchaseRequisitionApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/purchase-requisitions")
@RequiredArgsConstructor
public class PurchaseRequisitionApprovalController {

    private final PurchaseRequisitionApprovalService purchaseRequisitionApprovalService;

    // --------------------------------------------------
    //                       CREATE
    // --------------------------------------------------

    @PostMapping("/{requisitionId}/approvals")
    public ResponseEntity<PurchaseRequisitionApprovalDto> create(
            @PathVariable Long requisitionId,
            @RequestBody PurchaseRequisitionApprovalDto dto) {

        PurchaseRequisitionApprovalDto created =
                purchaseRequisitionApprovalService.create(requisitionId, dto);

        URI location = URI.create(
                "/api/purchase-requisitions/approvals/" + created.id()
        );

        return ResponseEntity.created(location).body(created);
    }

    // --------------------------------------------------
    //                        READ
    // --------------------------------------------------

    @GetMapping("/{requisitionId}/approvals")
    public ResponseEntity<List<PurchaseRequisitionApprovalDto>> getAll(
            @PathVariable Long requisitionId) {

        return ResponseEntity.ok(
                purchaseRequisitionApprovalService.getAll(requisitionId)
        );
    }

    @GetMapping("/approvals/{id}")
    public ResponseEntity<PurchaseRequisitionApprovalDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                purchaseRequisitionApprovalService.getById(id)
        );
    }

    // --------------------------------------------------
    //                       UPDATE
    // --------------------------------------------------

    @PutMapping("/approvals/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long reviewerId,
            @RequestBody PurchaseRequisitionApprovalDto dto) {

        purchaseRequisitionApprovalService.update(id, reviewerId, dto);

        return ResponseEntity.noContent().build();
    }

    // --------------------------------------------------
    //                       DELETE
    // --------------------------------------------------

    @DeleteMapping("/approvals/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        purchaseRequisitionApprovalService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
