package com.ijse.agms.zoneservice.Service.Impl;

import com.ijse.agms.zoneservice.DTO.ZoneDTO;
import com.ijse.agms.zoneservice.Entity.Zone;
import com.ijse.agms.zoneservice.Service.ZoneService;
import com.ijse.agms.zoneservice.repository.ZoneRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private ModelMapper modelMapper;


//    @Override
//    public ZoneDTO saveZone(ZoneDTO zoneDTO) {
//
//        if (zoneDTO.getName() == null || zoneDTO.getName().isEmpty() || zoneDTO.getDeviceId() == null || zoneDTO.getDeviceId().isEmpty() ) {
//            throw new IllegalArgumentException("Zone name or deviceId cannot be null or empty");
//        }
//
//        if (zoneDTO.getMinTemp() >= zoneDTO.getMaxTemp()) {
//            throw new IllegalArgumentException("Minimum temperature must be strictly less than maximum temperature");
//        }
//
//        Zone zone = modelMapper.map(zoneDTO, Zone.class);
//
//        zoneRepository.save(zone);
//
//        return modelMapper.map(zone, ZoneDTO.class);
//    }


    @Override
    public ZoneDTO saveZone(ZoneDTO zoneDTO) {

        // 1. මුලින්ම Zone Name එක සහ Temperatures ටික විතරක් check කරන්න
        if (zoneDTO.getName() == null || zoneDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Zone name cannot be null or empty");
        }

        if (zoneDTO.getMinTemp() >= zoneDTO.getMaxTemp()) {
            throw new IllegalArgumentException("Minimum temperature must be strictly less than maximum temperature");
        }

        // 2. Assignment එකට අනුව මෙතැනදී බාහිර සේවාවකට call කරලා deviceId එක ගන්න ඕනේ.
        // දැනට වැඩේ පරීක්ෂා කරගන්න අපි තාවකාලිකව ID එකක් දෙමු.
        // පසුව මෙතනට Feign Client Call එක දාන්න.
        String generatedDeviceId = "DEV-" + java.util.UUID.randomUUID().toString().substring(0, 8);

        // 3. Entity එකට map කරලා දත්ත ඇතුළත් කරන්න
        Zone zone = modelMapper.map(zoneDTO, Zone.class);
        zone.setDeviceId(generatedDeviceId); //生成的 deviceId එක මෙතනදී සෙට් කරනවා

        zoneRepository.save(zone);

        // 4. ආපසු DTO එකක් ලෙස යවන්න
        return modelMapper.map(zone, ZoneDTO.class);
    }

    @Override
    public ZoneDTO getZoneById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid zone ID");
        }

        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Zone not found with ID: " + id));

        return modelMapper.map(zone, ZoneDTO.class);
    }

    @Override
    public String updateZone(Long id, ZoneDTO zoneDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid zone ID");
        }

        if (zoneDTO.getName() == null || zoneDTO.getName().isEmpty() || zoneDTO.getDeviceId() == null || zoneDTO.getDeviceId().isEmpty() ) {
            throw new IllegalArgumentException("Zone name or deviceId cannot be null or empty");
        }

        if (zoneDTO.getMinTemp() >= zoneDTO.getMaxTemp()) {
            throw new IllegalArgumentException("Minimum temperature must be strictly less than maximum temperature");
        }

        Zone existingZone = zoneRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Zone not found with ID: " + id));

        existingZone.setName(zoneDTO.getName());
        existingZone.setMinTemp(zoneDTO.getMinTemp());
        existingZone.setMaxTemp(zoneDTO.getMaxTemp());
        existingZone.setDeviceId(zoneDTO.getDeviceId());

        zoneRepository.save(existingZone);

        return "Zone updated successfully";
    }

    @Override
    public boolean deleteZone(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid zone ID");
        }

        if (!zoneRepository.existsById(id)) {
            throw new IllegalArgumentException("Zone not found with ID: " + id);
        }

        zoneRepository.deleteById(id);
        return true;

    }


    @Override
    public List<ZoneDTO> getAllZones() {
        List<Zone> zones = zoneRepository.findAll();
        return zones.stream()
                .map(zone -> modelMapper.map(zone, ZoneDTO.class))
                .collect(Collectors.toList());
    }
}
