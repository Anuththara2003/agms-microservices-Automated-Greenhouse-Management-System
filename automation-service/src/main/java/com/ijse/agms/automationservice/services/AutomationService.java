package com.ijse.agms.automationservice.services;


import com.ijse.agms.automationservice.dto.TelemetryData;
import com.ijse.agms.automationservice.entity.AutomationLog;

import java.util.List;

public interface AutomationService {
    void processTelemetry(TelemetryData data);

    List<AutomationLog> findAllLogs(String userId);
}