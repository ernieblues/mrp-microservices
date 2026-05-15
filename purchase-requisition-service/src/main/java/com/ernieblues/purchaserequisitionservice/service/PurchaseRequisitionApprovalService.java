package com.ernieblues.purchaserequisitionservice.service;

import com.ernieblues.purchaserequisitionservice.dto.PurchaseRequisitionApprovalDto;
import com.ernieblues.purchaserequisitionservice.entity.ApprovalStatus;
import com.ernieblues.purchaserequisitionservice.entity.PurchaseRequisition;
import com.ernieblues.purchaserequisitionservice.entity.PurchaseRequisitionApproval;
import com.ernieblues.purchaserequisitionservice.repository.PurchaseRequisitionApprovalRepository;
import com.ernieblues.purchaserequisitionservice.repository.PurchaseRequisitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseRequisitionApprovalService {

    private final PurchaseRequisitionRepository purchaseRequisitionRepository;
    private final PurchaseRequisitionApprovalRepository purchaseRequisitionApprovalRepository;

    // --------------------------------------------------
    //                       CREATE
    // --------------------------------------------------

    @Transactional
    public PurchaseRequisitionApprovalDto create(
            Long requisitionId,
            PurchaseRequisitionApprovalDto dto) {

        PurchaseRequisition requisition = findRequisitionByIdOrThrow(requisitionId);

        PurchaseRequisitionApproval approval = new PurchaseRequisitionApproval();

        approval.setPurchaseRequisition(requisition);
        approval.setReviewerId(dto.reviewerId());
        approval.setApprovalStatus(ApprovalStatus.PENDING);
        approval.setComments(dto.comments());

        PurchaseRequisitionApproval saved =
                purchaseRequisitionApprovalRepository.save(approval);

        return mapToDto(saved);
    }

    // --------------------------------------------------
    //                        READ
    // --------------------------------------------------

    @Transactional(readOnly = true)
    public List<PurchaseRequisitionApprovalDto> getAll(Long requisitionId) {
        findRequisitionByIdOrThrow(requisitionId);

        return purchaseRequisitionApprovalRepository
                .findByPurchaseRequisition_Id(requisitionId)
                .stream()
                .map(PurchaseRequisitionApprovalService::mapToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public PurchaseRequisitionApprovalDto getById(Long id) {
        PurchaseRequisitionApproval approval = findApprovalByIdOrThrow(id);

        return mapToDto(approval);
    }

    // --------------------------------------------------
    //                       UPDATE
    // --------------------------------------------------

    @Transactional
    public void update(
            Long id,
            Long approvedById,
            PurchaseRequisitionApprovalDto dto) {

        PurchaseRequisitionApproval approval = findApprovalByIdOrThrow(id);

        if (!approval.getReviewerId().equals(approvedById)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        approval.setApprovalStatus(dto.approvalStatus());
        approval.setComments(dto.comments());

        approval.setDateReviewed(
                dto.approvalStatus() == ApprovalStatus.APPROVED
                        || dto.approvalStatus() == ApprovalStatus.REJECTED
                        ? OffsetDateTime.now()
                        : null
        );

        PurchaseRequisition requisition = approval.getPurchaseRequisition();
        requisition.calculateStatus();
    }

    // --------------------------------------------------
    //                       DELETE
    // --------------------------------------------------

    @Transactional
    public void delete(Long id) {
        PurchaseRequisitionApproval approval = findApprovalByIdOrThrow(id);

        PurchaseRequisition requisition = approval.getPurchaseRequisition();

        purchaseRequisitionApprovalRepository.delete(approval);

        requisition.getApprovals().remove(approval);
        requisition.calculateStatus();
    }

    // --------------------------------------------------
    //                      HELPERS
    // --------------------------------------------------

    private PurchaseRequisition findRequisitionByIdOrThrow(Long requisitionId) {
        return purchaseRequisitionRepository.findById(requisitionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private PurchaseRequisitionApproval findApprovalByIdOrThrow(Long id) {
        return purchaseRequisitionApprovalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private static PurchaseRequisitionApprovalDto mapToDto(
            PurchaseRequisitionApproval approval) {

        return new PurchaseRequisitionApprovalDto(
                approval.getId(),
                approval.getReviewerId(),
                approval.getDateReviewed(),
                approval.getApprovalStatus(),
                approval.getComments()
        );
    }
}
