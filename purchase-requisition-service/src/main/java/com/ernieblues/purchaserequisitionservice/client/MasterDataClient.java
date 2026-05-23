package com.ernieblues.purchaserequisitionservice.client;

import com.ernieblues.purchaserequisitionservice.dto.CostCenterDto;
import com.ernieblues.purchaserequisitionservice.dto.VendorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "master-data-service",
        url = "${services.master-data-service.url}"
)
public interface MasterDataClient {

    @GetMapping("/api/cost-centers/{id}")
    CostCenterDto getCostCenterById(@PathVariable Long id);

    @GetMapping("/api/vendors/{id}")
    VendorDto getVendorById(@PathVariable Long id);
}
