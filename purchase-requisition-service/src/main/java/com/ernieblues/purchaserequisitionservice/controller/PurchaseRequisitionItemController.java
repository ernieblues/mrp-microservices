package com.ernieblues.purchaserequisitionservice.controller;

import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionItemCreateDto;
import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionItemDto;
import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionItemUpdateDto;
import com.ernieblues.purchaserequisitionservice.service.PurchaseRequisitionItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/purchase-requisitions")
@RequiredArgsConstructor
public class PurchaseRequisitionItemController {

    private final PurchaseRequisitionItemService purchaseRequisitionItemService;

    // --------------------------------------------------
    //                       CREATE
    // --------------------------------------------------

    @PostMapping("/{requisitionId}/items")
    public ResponseEntity<PurchaseRequisitionItemDto> create(
            @PathVariable Long requisitionId,
            @RequestBody PurchaseRequisitionItemCreateDto dto) {

        PurchaseRequisitionItemDto created =
                purchaseRequisitionItemService.create(requisitionId, dto);

        URI location = URI.create(
                "/api/purchase-requisitions/items/" + created.id()
        );

        return ResponseEntity.created(location).body(created);
    }

    // --------------------------------------------------
    //                        READ
    // --------------------------------------------------

    @GetMapping("/{requisitionId}/items")
    public ResponseEntity<List<PurchaseRequisitionItemDto>> getAll(
            @PathVariable Long requisitionId) {

        return ResponseEntity.ok(
                purchaseRequisitionItemService.getAll(requisitionId)
        );
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<PurchaseRequisitionItemDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                purchaseRequisitionItemService.getById(id)
        );
    }

    // --------------------------------------------------
    //                       UPDATE
    // --------------------------------------------------

    @PutMapping("/items/{id}")
    public ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody PurchaseRequisitionItemUpdateDto dto) {

        purchaseRequisitionItemService.update(id, dto);

        return ResponseEntity.noContent().build();
    }

    // --------------------------------------------------
    //                       DELETE
    // --------------------------------------------------

    @DeleteMapping("/items/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        purchaseRequisitionItemService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
