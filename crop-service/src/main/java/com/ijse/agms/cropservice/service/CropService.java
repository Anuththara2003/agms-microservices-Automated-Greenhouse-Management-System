package com.ijse.agms.cropservice.service;

import com.ijse.agms.cropservice.dto.CropDTO;
import com.ijse.agms.cropservice.entity.CropStatus;

import java.util.List;

public interface CropService {
    CropDTO registerCrop(CropDTO cropDTO);

    CropDTO updateCropStatus(String id, CropStatus status);

    List<CropDTO> getAllCrops(String userId);
}