package com.ernieblues.purchaserequisitionservice.service;

import com.ernieblues.purchaserequisitionservice.client.MasterDataClient;
import com.ernieblues.purchaserequisitionservice.client.UserClient;
import com.ernieblues.purchaserequisitionservice.dto.CostCenterDto;
import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionApprovalDto;
import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionCreateDto;
import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionDetailDto;
import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionDto;
import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionItemDto;
import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionUpdateDto;
import com.ernieblues.purchaserequisitionservice.dto.UserDto;
import com.ernieblues.purchaserequisitionservice.dto.VendorDto;
import com.ernieblues.purchaserequisitionservice.entity.PurchaseRequisition;
import com.ernieblues.purchaserequisitionservice.entity.PurchaseRequisitionApproval;
import com.ernieblues.purchaserequisitionservice.entity.PurchaseRequisitionItem;
import com.ernieblues.purchaserequisitionservice.repository.PurchaseRequisitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseRequisitionService {

    private final PurchaseRequisitionRepository purchaseRequisitionRepository;
    private final UserClient userClient;
    private final MasterDataClient masterDataClient;

    // --------------------------------------------------
    //                       CREATE
    // --------------------------------------------------

    @Transactional
    public PurchaseRequisitionDto create(Long requestedById, PurchaseRequisitionCreateDto dto) {
        OffsetDateTime dateRequested = OffsetDateTime.now();

        if (dto.dateRequired().isBefore(dateRequested)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "DateRequired must be today or later."
            );
        }

        PurchaseRequisition requisition = new PurchaseRequisition();

        requisition.setRequestedById(requestedById);
        requisition.setDateRequested(dateRequested);
        requisition.setDateRequired(dto.dateRequired());
        requisition.setCostCenterId(dto.costCenterId());
        requisition.setVendorId(dto.vendorId());
        requisition.setComments(dto.comments());

        List<PurchaseRequisitionItem> items = dto.items().stream()
                .map(itemDto -> {
                    PurchaseRequisitionItem item = new PurchaseRequisitionItem();

                    item.setPurchaseRequisition(requisition);
                    item.setLineNumber(itemDto.lineNumber());
                    item.setProductId(itemDto.productId());
                    item.setDescription(itemDto.description());
                    item.setVendorPartNumber(itemDto.vendorPartNumber());
                    item.setQuantity(itemDto.quantity());
                    item.setUnitOfMeasure(itemDto.unitOfMeasure());
                    item.setUnitPrice(itemDto.unitPrice());

                    return item;
                })
                .toList();

        requisition.setItems(items);

        PurchaseRequisition saved = purchaseRequisitionRepository.save(requisition);

        return mapToDto(saved);
    }

    // --------------------------------------------------
    //                        READ
    // --------------------------------------------------

    @Transactional(readOnly = true)
    public List<PurchaseRequisitionDetailDto> getAll() {
        return purchaseRequisitionRepository.findAll().stream()
                .sorted(Comparator.comparing(
                                PurchaseRequisition::getDateRequested)
                        .reversed())
                .map(requisition -> {
                    UserDto user =
                            userClient.getById(
                                    requisition.getRequestedById());

                    CostCenterDto costCenter =
                            masterDataClient.getCostCenterById(
                                    requisition.getCostCenterId());

                    VendorDto vendor =
                            masterDataClient.getVendorById(
                                    requisition.getVendorId());

                    return mapToDetailDto(
                            requisition,
                            user,
                            costCenter,
                            vendor);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public PurchaseRequisitionDetailDto getById(Long id) {
        PurchaseRequisition requisition = findByIdOrThrow(id);

        UserDto user =
                userClient.getById(
                        requisition.getRequestedById());

        CostCenterDto costCenter =
                masterDataClient.getCostCenterById(
                        requisition.getCostCenterId());

        VendorDto vendor =
                masterDataClient.getVendorById(
                        requisition.getVendorId());

        return mapToDetailDto(
                requisition,
                user,
                costCenter,
                vendor);
    }

    // --------------------------------------------------
    //                       UPDATE
    // --------------------------------------------------

    @Transactional
    public PurchaseRequisitionDto update(Long id, PurchaseRequisitionUpdateDto dto) {
        PurchaseRequisition requisition = findByIdOrThrow(id);

        if (dto.dateRequired().isBefore(OffsetDateTime.now())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "DateRequired must be today or later."
            );
        }

        requisition.setDateRequired(dto.dateRequired());
        requisition.setCostCenterId(dto.costCenterId());
        requisition.setVendorId(dto.vendorId());
        requisition.setComments(dto.comments());

        PurchaseRequisition saved = purchaseRequisitionRepository.save(requisition);

        return mapToDto(saved);
    }

    // --------------------------------------------------
    //                       DELETE
    // --------------------------------------------------

    @Transactional
    public void delete(Long id) {
        PurchaseRequisition requisition = findByIdOrThrow(id);

        purchaseRequisitionRepository.delete(requisition);
    }

    // --------------------------------------------------
    //                      HELPERS
    // --------------------------------------------------

    private PurchaseRequisition findByIdOrThrow(Long id) {
        return purchaseRequisitionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private static PurchaseRequisitionApprovalDto mapToApprovalDto(
            PurchaseRequisitionApproval approval) {

        return new PurchaseRequisitionApprovalDto(
                approval.getId(),
                approval.getReviewerId(),
                approval.getDateReviewed(),
                approval.getApprovalStatus(),
                approval.getComments()
        );
    }

    private static PurchaseRequisitionDetailDto mapToDetailDto(
            PurchaseRequisition pr,
            UserDto user,
            CostCenterDto costCenter,
            VendorDto vendor) {

        return new PurchaseRequisitionDetailDto(
                pr.getId(),
                pr.getPurchaseRequisitionNumber(),
                pr.getDateRequested(),
                pr.getDateRequired(),
                user,
                costCenter,
                vendor,
                pr.getComments(),
                pr.getTotalCost(),
                pr.getStatus(),
                pr.getItems().stream()
                        .map(PurchaseRequisitionService::mapToItemDto)
                        .toList(),
                pr.getApprovals().stream()
                        .map(PurchaseRequisitionService::mapToApprovalDto)
                        .toList()
        );
    }

    private static PurchaseRequisitionDto mapToDto(PurchaseRequisition pr) {
        return new PurchaseRequisitionDto(
                pr.getId(),
                pr.getPurchaseRequisitionNumber(),
                pr.getDateRequested(),
                pr.getDateRequired(),
                pr.getRequestedById(),
                pr.getCostCenterId(),
                pr.getVendorId(),
                pr.getComments(),
                pr.getTotalCost(),
                pr.getStatus(),
                pr.getItems().stream()
                        .map(PurchaseRequisitionService::mapToItemDto)
                        .toList(),
                pr.getApprovals().stream()
                        .map(PurchaseRequisitionService::mapToApprovalDto)
                        .toList()
        );
    }

    private static PurchaseRequisitionItemDto mapToItemDto(PurchaseRequisitionItem item) {
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
