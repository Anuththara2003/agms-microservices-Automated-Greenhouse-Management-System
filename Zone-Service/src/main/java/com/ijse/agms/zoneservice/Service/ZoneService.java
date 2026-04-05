package com.ijse.agms.zoneservice.Service;


import com.ijse.agms.zoneservice.DTO.ZoneDTO;

import java.util.List;

public interface ZoneService {
    ZoneDTO saveZone(ZoneDTO zoneDTO);

    ZoneDTO getZoneById(Long id);

    String updateZone(Long id, ZoneDTO zoneDTO);

    boolean deleteZone(Long id);
    List<ZoneDTO> getAllZones();
}