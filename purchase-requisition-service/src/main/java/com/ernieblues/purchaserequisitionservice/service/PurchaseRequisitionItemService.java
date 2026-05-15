package com.ernieblues.purchaserequisitionservice.service;

import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionItemCreateDto;
import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionItemDto;
import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionItemUpdateDto;
import com.ernieblues.purchaserequisitionservice.entity.PurchaseRequisition;
import com.ernieblues.purchaserequisitionservice.entity.PurchaseRequisitionItem;
import com.ernieblues.purchaserequisitionservice.repository.PurchaseRequisitionItemRepository;
import com.ernieblues.purchaserequisitionservice.repository.PurchaseRequisitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseRequisitionItemService {

    private final PurchaseRequisitionRepository purchaseRequisitionRepository;
    private final PurchaseRequisitionItemRepository purchaseRequisitionItemRepository;

    // --------------------------------------------------
    //                       CREATE
    // --------------------------------------------------

    @Transactional
    public PurchaseRequisitionItemDto create(
            Long requisitionId,
            PurchaseRequisitionItemCreateDto dto) {

        PurchaseRequisition requisition = findRequisitionByIdOrThrow(requisitionId);

        List<PurchaseRequisitionItem> items = getItems(requisitionId);

        boolean hasRequestedLineNumber =
                dto.lineNumber() != null && dto.lineNumber() > 0;

        int lineNumber = hasRequestedLineNumber
                ? dto.lineNumber()
                : items.isEmpty()
                  ? 1
                  : items.stream()
                    .mapToInt(PurchaseRequisitionItem::getLineNumber)
                    .max()
                    .orElse(0) + 1;

        PurchaseRequisitionItem item = new PurchaseRequisitionItem();

        item.setPurchaseRequisition(requisition);
        item.setLineNumber(lineNumber);
        item.setProductId(dto.productId());
        item.setDescription(dto.description());
        item.setVendorPartNumber(dto.vendorPartNumber());
        item.setQuantity(dto.quantity());
        item.setUnitOfMeasure(normalizeUnitOfMeasure(dto.unitOfMeasure()));
        item.setUnitPrice(dto.unitPrice());

        PurchaseRequisitionItem saved =
                purchaseRequisitionItemRepository.save(item);

        items.add(saved);

        if (hasRequestedLineNumber) {
            sortItems(items, saved.getId());
        }

        return mapToDto(saved);
    }

    // --------------------------------------------------
    //                        READ
    // --------------------------------------------------

    @Transactional(readOnly = true)
    public List<PurchaseRequisitionItemDto> getAll(Long requisitionId) {
        findRequisitionByIdOrThrow(requisitionId);

        return purchaseRequisitionItemRepository
                .findByPurchaseRequisition_IdOrderByLineNumberAscIdAsc(requisitionId)
                .stream()
                .map(PurchaseRequisitionItemService::mapToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public PurchaseRequisitionItemDto getById(Long id) {
        PurchaseRequisitionItem item = findItemByIdOrThrow(id);

        return mapToDto(item);
    }

    // --------------------------------------------------
    //                       UPDATE
    // --------------------------------------------------

    @Transactional
    public void update(
            Long id,
            PurchaseRequisitionItemUpdateDto dto) {

        PurchaseRequisitionItem item = findItemByIdOrThrow(id);

        Long requisitionId = item.getPurchaseRequisition().getId();

        item.setProductId(dto.productId());
        item.setDescription(dto.description());
        item.setVendorPartNumber(dto.vendorPartNumber());
        item.setQuantity(dto.quantity());
        item.setUnitOfMeasure(normalizeUnitOfMeasure(dto.unitOfMeasure()));
        item.setUnitPrice(dto.unitPrice());

        if (dto.lineNumber() != null && dto.lineNumber() > 0) {
            item.setLineNumber(dto.lineNumber());

            List<PurchaseRequisitionItem> items = getItems(requisitionId);
            sortItems(items, item.getId());
        }
    }

    // --------------------------------------------------
    //                       DELETE
    // --------------------------------------------------

    @Transactional
    public void delete(Long id) {
        PurchaseRequisitionItem item = findItemByIdOrThrow(id);

        Long requisitionId = item.getPurchaseRequisition().getId();

        purchaseRequisitionItemRepository.delete(item);

        List<PurchaseRequisitionItem> items = getItems(requisitionId);

        sortItems(items);
    }

    // --------------------------------------------------
    //                      HELPERS
    // --------------------------------------------------

    private PurchaseRequisition findRequisitionByIdOrThrow(Long requisitionId) {
        return purchaseRequisitionRepository.findById(requisitionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private PurchaseRequisitionItem findItemByIdOrThrow(Long id) {
        return purchaseRequisitionItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private List<PurchaseRequisitionItem> getItems(Long requisitionId) {
        return purchaseRequisitionItemRepository
                .findByPurchaseRequisition_IdOrderByLineNumberAscIdAsc(requisitionId);
    }

    private void sortItems(List<PurchaseRequisitionItem> items) {
        sortItems(items, null);
    }

    private void sortItems(
            List<PurchaseRequisitionItem> items,
            Long preferredItemId) {

        List<PurchaseRequisitionItem> sorted = items.stream()
                .sorted(
                        Comparator
                                .comparing(PurchaseRequisitionItem::getLineNumber)
                                .thenComparing(
                                        item -> preferredItemId != null
                                                && item.getId().equals(preferredItemId)
                                                ? 0
                                                : 1
                                )
                )
                .toList();

        for (int i = 0; i < sorted.size(); i++) {
            sorted.get(i).setLineNumber(i + 1);
        }
    }

    private static String normalizeUnitOfMeasure(String unitOfMeasure) {
        if (unitOfMeasure == null || unitOfMeasure.isBlank()) {
            return "EA";
        }

        return unitOfMeasure.trim();
    }

    private static PurchaseRequisitionItemDto mapToDto(PurchaseRequisitionItem item) {
        return new PurchaseRequisitionItemDto(
                item.getId(),
                item.getLineNumber(),
                item.getProductId(),
                item.getDescription(),
                item.getVendorPartNumber(),
                item.getQuantity(),
                item.getUnitOfMeasure(),
                item.getUnitPrice(),
                item.getTotalPrice()
        );
    }
}
