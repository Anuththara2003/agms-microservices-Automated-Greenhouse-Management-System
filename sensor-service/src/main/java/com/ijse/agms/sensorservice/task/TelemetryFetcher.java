package com.ijse.agms.sensorservice.task;

import com.ijse.agms.sensorservice.Client.AutomationClient;
import com.ijse.agms.sensorservice.Controller.SensorController;
import com.ijse.agms.sensorservice.dto.TelemetryData;
import com.ijse.agms.sensorservice.dto.TelemetryValue;
import com.ijse.agms.sensorservice.dto.ZoneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelemetryFetcher {

    @Autowired
    private SensorController sensorController;

    @Autowired
    private AutomationClient automationClient;

    @Scheduled(fixedRate = 10000)
    public void fetch() {
        try {
            System.out.println("--- Starting Telemetry Fetch Cycle (MOCKED) ---");


            List<ZoneDTO> zones = new ArrayList<>();
            zones.add(new ZoneDTO("1", "Tomato Zone", 20.5, 30.0, "DEV-001"));
            zones.add(new ZoneDTO("2", "Orchid Zone", 18.0, 25.0, "DEV-002"));

            for (ZoneDTO zone : zones) {

                TelemetryData data = new TelemetryData();
                TelemetryValue value = new TelemetryValue();

                double randomTemp = 22 + (Math.random() * 13);
                double randomHum = 50 + (Math.random() * 20);

                value.setTemperature(Math.round(randomTemp * 100.0) / 100.0);
                value.setHumidity(Math.round(randomHum * 100.0) / 100.0);

                data.setValue(value);
                data.setZoneId(zone.getId());
                data.setDeviceId(zone.getDeviceId());


                sensorController.updateReading(zone.getId(), data);


                try {
                    automationClient.sendToAutomation(data);
                    System.out.println(">>> Successfully Pushed to Automation Service for Zone: " + zone.getName());
                } catch (Exception e) {
                    System.err.println("!!! Failed to push to Automation Service: " + e.getMessage());
                }

                System.out.println("MOCK DATA -> Zone: " + zone.getName() +
                        " | Temp: " + value.getTemperature() + "°C" +
                        " | Hum: " + value.getHumidity() + "%");
            }
            System.out.println("--- Fetch Cycle Completed ---");

        } catch (Exception e) {
            System.err.println("Critical error in TelemetryFetcher: " + e.getMessage());
            e.printStackTrace();
        }
    }
}