package com.ijse.agms.sensorservice.ExternalAuthService;

import com.ijse.agms.sensorservice.dto.DeviceDTO;

public interface ExternalAuthService {
    DeviceDTO registerDeviceAtExternalApi(DeviceDTO deviceDTO);
}
