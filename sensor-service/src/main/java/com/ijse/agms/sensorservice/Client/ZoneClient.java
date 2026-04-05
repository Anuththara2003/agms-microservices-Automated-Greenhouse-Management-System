package com.ijse.agms.sensorservice.Client;

import com.ijse.agms.sensorservice.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ZONE-SERVICE")
public interface ZoneClient {
    @GetMapping("/api/zones")
    ApiResponse getAllZones();
}