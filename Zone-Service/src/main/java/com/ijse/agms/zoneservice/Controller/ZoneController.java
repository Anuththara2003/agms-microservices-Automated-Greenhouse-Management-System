package com.ijse.agms.zoneservice.Controller;


import com.ijse.agms.zoneservice.DTO.ApiResponse;
import com.ijse.agms.zoneservice.DTO.ZoneDTO;
import com.ijse.agms.zoneservice.Service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/api/zones")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;


    @PostMapping
    public ResponseEntity<ApiResponse> createZone(@RequestBody ZoneDTO zoneDTO) {
        return ResponseEntity.ok(
                new ApiResponse(
                        201,
                        "Zone created",
                        zoneService.saveZone(zoneDTO)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllZones() {
        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "Success",
                        zoneService.getAllZones()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getZone(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "Success",
                        zoneService.getZoneById(id)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateZone(@PathVariable Long id, @RequestBody ZoneDTO zoneDTO) {
        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "Zone updated",
                        zoneService.updateZone(id, zoneDTO)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteZone(@PathVariable Long id) {
        zoneService.deleteZone(id);
        return ResponseEntity.ok(
                new ApiResponse(
                        200,
                        "Zone deleted",
                        null
                )
        );
    }
}