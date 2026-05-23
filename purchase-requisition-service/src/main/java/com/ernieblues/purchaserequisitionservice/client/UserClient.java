package com.ernieblues.purchaserequisitionservice.client;

import com.ernieblues.purchaserequisitionservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "user-service",
        url = "${services.user-service.url}"
)
public interface UserClient {

    @GetMapping("/api/users/{id}")
    UserDto getById(@PathVariable Long id);
}
