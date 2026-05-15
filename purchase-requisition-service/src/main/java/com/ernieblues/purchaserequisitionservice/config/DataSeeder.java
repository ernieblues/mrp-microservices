package com.ernieblues.purchaserequisitionservice.config;

import com.ernieblues.purchaserequisitionservice.entity.*;
import com.ernieblues.purchaserequisitionservice.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Configuration
@Profile({"local", "k8s"})
public class DataSeeder {

    @Bean
    CommandLineRunner seedData(PurchaseRequisitionRepository purchaseRequisitionRepository) {

        return args -> {

            if (purchaseRequisitionRepository.count() == 0) {

				Long[] userIds = {1L, 2L, 3L, 4L, 5L, 6L};
				Long[] costCenterIds = { 1L, 2L, 3L };
				Long[] vendorIds     = { 1L, 2L, 3L };

				PurchaseRequisition pr1 = new PurchaseRequisition();
				pr1.setRequestedById(userIds[3]);
				pr1.setDateRequested(OffsetDateTime.now());
				pr1.setDateRequired(OffsetDateTime.now());
				pr1.setCostCenterId(costCenterIds[0]);
				pr1.setVendorId(vendorIds[0]);
				pr1.setComments("ACME product evaluation");

				PurchaseRequisitionItem item1 = new PurchaseRequisitionItem();
				item1.setLineNumber(1);
				item1.setDescription("Rocket Skates");
				item1.setQuantity(new BigDecimal("1"));
				item1.setUnitOfMeasure("EA");
				item1.setUnitPrice(new BigDecimal("499.99"));
				item1.setPurchaseRequisition(pr1);

				PurchaseRequisitionItem item2 = new PurchaseRequisitionItem();
				item2.setLineNumber(2);
				item2.setDescription("Anvil (Heavy Duty)");
				item2.setQuantity(new BigDecimal("3"));
				item2.setUnitOfMeasure("EA");
				item2.setUnitPrice(new BigDecimal("129.50"));
				item2.setPurchaseRequisition(pr1);

				PurchaseRequisitionItem item3 = new PurchaseRequisitionItem();
				item3.setLineNumber(3);
				item3.setDescription("Giant Rubber Band");
				item3.setQuantity(new BigDecimal("2"));
				item3.setUnitOfMeasure("EA");
				item3.setUnitPrice(new BigDecimal("39.95"));
				item3.setPurchaseRequisition(pr1);

				PurchaseRequisitionApproval approval1 = new PurchaseRequisitionApproval();
				approval1.setReviewerId(userIds[0]);
				approval1.setApprovalStatus(ApprovalStatus.APPROVED);
				approval1.setDateReviewed(OffsetDateTime.now());
				approval1.setComments("");
				approval1.setPurchaseRequisition(pr1);

				pr1.setItems(List.of(item1, item2, item3));
				pr1.setApprovals(List.of(approval1));

				// --- PR 2 ---
				PurchaseRequisition pr2 = new PurchaseRequisition();
				pr2.setRequestedById(userIds[4]);
				pr2.setDateRequested(OffsetDateTime.now());
				pr2.setDateRequired(OffsetDateTime.now().plusDays(7));
				pr2.setCostCenterId(costCenterIds[1]);
				pr2.setVendorId(vendorIds[1]);
				pr2.setComments("office equipment");

				PurchaseRequisitionItem item4 = new PurchaseRequisitionItem();
				item4.setLineNumber(1);
				item4.setDescription("Laptop");
				item4.setQuantity(new BigDecimal("2"));
				item4.setUnitOfMeasure("EA");
				item4.setUnitPrice(new BigDecimal("1199.99"));
				item4.setPurchaseRequisition(pr2);

				PurchaseRequisitionItem item5 = new PurchaseRequisitionItem();
				item5.setLineNumber(2);
				item5.setDescription("Mouse");
				item5.setQuantity(new BigDecimal("5"));
				item5.setUnitOfMeasure("EA");
				item5.setUnitPrice(new BigDecimal("24.99"));
				item5.setPurchaseRequisition(pr2);

				PurchaseRequisitionApproval approval2 = new PurchaseRequisitionApproval();
				approval2.setReviewerId(userIds[2]);
				approval2.setApprovalStatus(ApprovalStatus.APPROVED);
				approval2.setDateReviewed(OffsetDateTime.now());
				approval2.setComments("Looks good.");
				approval2.setPurchaseRequisition(pr2);

				PurchaseRequisitionApproval approval3 = new PurchaseRequisitionApproval();
				approval3.setReviewerId(userIds[1]);
				approval3.setApprovalStatus(ApprovalStatus.PENDING);
				approval3.setDateReviewed(OffsetDateTime.now());
				approval3.setComments("");
				approval3.setPurchaseRequisition(pr2);

				pr2.setItems(List.of(item4, item5));
				pr2.setApprovals(List.of(approval2, approval3));

				// --- PR 3 ---
				PurchaseRequisition pr3 = new PurchaseRequisition();
				pr3.setRequestedById(userIds[5]);
				pr3.setDateRequested(OffsetDateTime.now());
				pr3.setDateRequired(OffsetDateTime.now().plusDays(14));
				pr3.setCostCenterId(costCenterIds[2]);
				pr3.setVendorId(vendorIds[2]);
				pr3.setComments("shop supplies");

				PurchaseRequisitionItem item6 = new PurchaseRequisitionItem();
				item6.setLineNumber(1);
				item6.setDescription("Drill Press");
				item6.setQuantity(new BigDecimal("1"));
				item6.setUnitOfMeasure("EA");
				item6.setUnitPrice(new BigDecimal("899.50"));
				item6.setPurchaseRequisition(pr3);

				PurchaseRequisitionItem item7 = new PurchaseRequisitionItem();
				item7.setLineNumber(2);
				item7.setDescription("Safety Glasses");
				item7.setQuantity(new BigDecimal("10"));
				item7.setUnitOfMeasure("EA");
				item7.setUnitPrice(new BigDecimal("9.75"));
				item7.setPurchaseRequisition(pr3);

				PurchaseRequisitionApproval approval4 = new PurchaseRequisitionApproval();
				approval4.setReviewerId(userIds[2]);
				approval4.setApprovalStatus(ApprovalStatus.REJECTED);
				approval4.setDateReviewed(OffsetDateTime.now());
				approval4.setComments("");
				approval4.setPurchaseRequisition(pr3);

				PurchaseRequisitionApproval approval5 = new PurchaseRequisitionApproval();
				approval5.setReviewerId(userIds[1]);
				approval5.setApprovalStatus(ApprovalStatus.PENDING);
				approval5.setDateReviewed(OffsetDateTime.now());
				approval5.setComments("");
				approval5.setPurchaseRequisition(pr3);

				pr3.setItems(List.of(item6, item7));
				pr3.setApprovals(List.of(approval4, approval5));

				purchaseRequisitionRepository.saveAll(List.of(pr1, pr2, pr3));
			}
        };
    }
}
