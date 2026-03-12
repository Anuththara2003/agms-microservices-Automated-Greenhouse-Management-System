package com.ijse.agms.sensorservice.dto;

public class DeviceDTO {
    private String deviceId;
    private String name;
    private String zoneId;

    public DeviceDTO(String deviceId, String name, String zoneId) {
        this.deviceId = deviceId;
        this.name = name;
        this.zoneId = zoneId;
    }

    public DeviceDTO() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
