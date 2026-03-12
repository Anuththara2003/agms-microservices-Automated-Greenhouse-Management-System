package com.ijse.agms.zoneservice.repository;


import com.ijse.agms.zoneservice.Entity.Zone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ZoneRepository extends JpaRepository<Zone, Long> {
    Optional<Zone> findByDeviceId(String deviceId);
}
