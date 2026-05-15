package com.ernieblues.purchaserequisitionservice.controller;

import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionCreateDto;
import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionDetailDto;
import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionDto;
import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionUpdateDto;
import com.ernieblues.purchaserequisitionservice.entity.ApprovalStatus;
import com.ernieblues.purchaserequisitionservice.service.PurchaseRequisitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/purchase-requisitions")
@RequiredArgsConstructor
public class PurchaseRequisitionController {

    private final PurchaseRequisitionService purchaseRequisitionService;

    // --------------------------------------------------
    //                       CREATE
    // --------------------------------------------------

    @PostMapping
    public ResponseEntity<PurchaseRequisitionDto> create(
            @RequestHeader("X-User-Id") Long requestedById,
            @RequestBody PurchaseRequisitionCreateDto dto) {

        PurchaseRequisitionDto created =
                purchaseRequisitionService.create(requestedById, dto);

        URI location = URI.create(
                "/api/purchase-requisitions/" + created.id()
        );

        return ResponseEntity.created(location).body(created);
    }

    // --------------------------------------------------
    //                        READ
    // --------------------------------------------------

    @GetMapping
    public ResponseEntity<List<PurchaseRequisitionDetailDto>> getAll() {
        return ResponseEntity.ok(purchaseRequisitionService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseRequisitionDetailDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseRequisitionService.getById(id));
    }

    // --------------------------------------------------
    //                       UPDATE
    // --------------------------------------------------

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseRequisitionDto> update(
            @PathVariable Long id,
            @RequestBody PurchaseRequisitionUpdateDto dto) {

        return ResponseEntity.ok(purchaseRequisitionService.update(id, dto));
    }

    // --------------------------------------------------
    //                       DELETE
    // --------------------------------------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        purchaseRequisitionService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
