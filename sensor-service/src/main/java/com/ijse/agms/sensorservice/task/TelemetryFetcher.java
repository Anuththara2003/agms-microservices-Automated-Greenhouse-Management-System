package com.ijse.agms.sensorservice.task;

import com.ijse.agms.sensorservice.Client.ZoneClient;
import com.ijse.agms.sensorservice.Controller.SensorController;
import com.ijse.agms.sensorservice.Impl.ExternalAuthServiceImpl;
import com.ijse.agms.sensorservice.dto.TelemetryData;
import com.ijse.agms.sensorservice.dto.ZoneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpHeaders;
import java.util.List;

@Component
public class TelemetryFetcher {

    @Autowired
    private ExternalAuthServiceImpl authService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SensorController sensorController;

    @Autowired
    private ZoneClient zoneClient;

    @Value("${external.iot.base-url}")
    private String baseUrl;

    @Scheduled(fixedRate = 10000)
    public void fetch() {
        List<ZoneDTO> zones;
        try {
            zones = zoneClient.getAllZones();
        } catch (Exception e) {
            System.err.println("Could not fetch zones from zone-service: " + e.getMessage());
            return;
        }

        if (zones == null || zones.isEmpty()) return;

        String token = authService.getAccessToken();
        if (token == null) return;

        for (ZoneDTO zone : zones) {
            String deviceId = zone.getDeviceId();

            if (deviceId == null || deviceId.isEmpty()) continue;

            String url = baseUrl + "/devices/telemetry/" + deviceId;
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            try {
                ResponseEntity<TelemetryData> response = restTemplate.exchange(url, HttpMethod.GET, entity, TelemetryData.class);

                if (response.getStatusCode() == HttpStatus.OK) {
                    TelemetryData data = response.getBody();

                    sensorController.updateReading(zone.getId(), data);

                    System.out.println("Zone: " + zone.getName() + " | Temp: " + data.getValue().getTemperature());

                }
            } catch (Exception e) {
                System.err.println("Error fetching for device " + deviceId + ": " + e.getMessage());
            }
        }
    }
}