package com.ijse.agms.cropservice.repository;

import com.ijse.agms.cropservice.entity.Crop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CropRepository extends JpaRepository<Crop, String> {
    List<Crop> findByUserId(String userId);

}