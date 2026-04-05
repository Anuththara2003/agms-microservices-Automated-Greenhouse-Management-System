package com.ijse.agms.cropservice.controller;

import com.ijse.agms.cropservice.dto.ApiResponse;
import com.ijse.agms.cropservice.dto.CropDTO;
import com.ijse.agms.cropservice.entity.CropStatus;
import com.ijse.agms.cropservice.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crops")
@CrossOrigin("*")
public class CropController {

    @Autowired
    private CropService cropService;

    @PostMapping
    public ResponseEntity<ApiResponse> registerCrop(@RequestBody CropDTO cropDTO) {
        cropDTO.setStatus(CropStatus.SEEDLING);
        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "Crop registered successfully",
                        cropService.registerCrop(cropDTO)
                )
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse> updateCropStatus(@PathVariable String id, @RequestParam CropStatus status) {
        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "Crop status updated successfully",
                        cropService.updateCropStatus(id, status)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCrops(@RequestParam String userId) {
        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "Crops retrieved successfully",
                        cropService.getAllCrops(userId)
                )
        );
    }

}