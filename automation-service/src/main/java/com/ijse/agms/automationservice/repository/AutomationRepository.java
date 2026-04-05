package com.ijse.agms.automationservice.repository;

import com.ijse.agms.automationservice.entity.AutomationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutomationRepository extends JpaRepository<AutomationLog, Long> {
    List<AutomationLog> findByUserId(String userId);
}