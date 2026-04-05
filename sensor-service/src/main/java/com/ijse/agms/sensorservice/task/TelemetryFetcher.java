package com.ijse.agms.sensorservice.task;

import com.ijse.agms.sensorservice.Client.AutomationClient;
import com.ijse.agms.sensorservice.Client.ZoneClient;
import com.ijse.agms.sensorservice.Impl.ExternalAuthServiceImpl;
import com.ijse.agms.sensorservice.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@Component
public class TelemetryFetcher {

    @Autowired
    private ZoneClient zoneClient;

    @Autowired
    private ExternalAuthServiceImpl authService;

    @Autowired
    private AutomationClient automationClient;

    @Value("${external.iot.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10000)
    public void fetch() {
        System.out.println("--- Starting Real Telemetry Fetch Cycle ---");


        ApiResponse zoneResponse = zoneClient.getAllZones();
        if (zoneResponse == null || zoneResponse.getData() == null) return;


        List<java.util.Map<String, Object>> zones = (List<java.util.Map<String, Object>>) zoneResponse.getData();


        String token = authService.getAccessToken();

        for (java.util.Map<String, Object> zone : zones) {
            String deviceId = (String) zone.get("deviceId");
            String zoneId = String.valueOf(zone.get("id"));

            if (deviceId == null) continue;

            try {

                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(token);
                HttpEntity<String> entity = new HttpEntity<>(headers);

                String url = baseUrl + "/devices/telemetry/" + deviceId;
                ResponseEntity<TelemetryData> response = restTemplate.exchange(url, HttpMethod.GET, entity, TelemetryData.class);

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    TelemetryData realData = response.getBody();
                    realData.setZoneId(zoneId);
                    automationClient.sendToAutomation(realData);
                    System.out.println("SUCCESS: Real Data fetched for Zone " + zoneId);
                    continue;
                }
            } catch (Exception e) {
                System.err.println("External API unreachable for device " + deviceId + ". Switching to Mock Data.");
            }


            sendMockData(zoneId, deviceId);
        }
    }

    private void sendMockData(String zoneId, String deviceId) {
        TelemetryData mockData = new TelemetryData();
        TelemetryValue val = new TelemetryValue();
        val.setTemperature(25 + (Math.random() * 10));
        val.setHumidity(60.0);
        mockData.setValue(val);
        mockData.setZoneId(zoneId);
        mockData.setDeviceId(deviceId);
        automationClient.sendToAutomation(mockData);
        System.out.println("MOCKED: Data sent for Zone " + zoneId + " (External API Down)");
    }
}