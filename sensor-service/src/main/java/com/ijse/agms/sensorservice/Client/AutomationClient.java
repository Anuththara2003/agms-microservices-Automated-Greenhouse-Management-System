package com.ijse.agms.sensorservice.Client;


import com.ijse.agms.sensorservice.dto.TelemetryData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AUTOMATION-SERVICE")
public interface AutomationClient {
    @PostMapping("/api/automation/process")
    void sendToAutomation(@RequestBody TelemetryData data);
}