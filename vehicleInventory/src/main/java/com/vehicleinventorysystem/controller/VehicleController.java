package com.vehicleinventorysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vehicleinventorysystem.model.Vehicle;
import com.vehicleinventorysystem.service.VehicleService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/getAll")
    public ResponseEntity<Map<String, Object>> getAllVehicles() {
        try {
            List<Vehicle> vehicles = vehicleService.getAllVehicles();
            return ResponseEntity.ok(Map.of("status", "success", "data", vehicles));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", "Failed to fetch vehicles", "error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getVehicleById(@PathVariable int id) {
        try {
            Vehicle vehicle = vehicleService.getVehicleById(id);
            if (vehicle != null) {
                return ResponseEntity.ok(Map.of("status", "success", "data", vehicle));
            }
            return ResponseEntity.status(404).body(Map.of("status", "error", "message", "Vehicle not found"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", "Failed to fetch vehicle", "error", e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> createVehicle(@RequestBody Vehicle vehicle) {
        try {
            Vehicle savedVehicle = vehicleService.saveVehicle(vehicle);
            return ResponseEntity.ok(Map.of("status", "success", "data", savedVehicle));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", "Failed to add vehicle", "error", e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateVehicle(@PathVariable int id, @RequestBody Vehicle vehicle) {
        try {
            Vehicle updatedVehicle = vehicleService.updateVehicle(id, vehicle);
            if (updatedVehicle != null) {
                return ResponseEntity.ok(Map.of("status", "success", "data", updatedVehicle));
            }
            return ResponseEntity.status(404).body(Map.of("status", "error", "message", "Vehicle not found"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", "Failed to update vehicle", "error", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> deleteVehicle(@PathVariable int id) {
        try {
            String message = vehicleService.deleteVehicle(id);
            if (message.contains("deleted successfully")) {
                return ResponseEntity.ok(Map.of("status", "success", "message", message));
            }
            return ResponseEntity.status(404).body(Map.of("status", "error", "message", message));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", "Failed to delete vehicle", "error", e.getMessage()));
        }
    }

    @GetMapping("/findbybrandname/{brandName}")
    public ResponseEntity<Map<String, Object>> findVehiclesByBrandName(@PathVariable String brandName) {
        try {
            List<Vehicle> vehicles = vehicleService.findVehiclesByBrandName(brandName);
            return ResponseEntity.ok(Map.of("status", "success", "data", vehicles));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", "Failed to fetch vehicles by brand name", "error", e.getMessage()));
        }
    }
    
    @GetMapping("/search/name{vehicleName}")
    public ResponseEntity<Map<String, Object>> searchVehicleByName(@PathVariable String vehicleName) {
        try {
            List<Vehicle> vehicles = vehicleService.searchVehicleByName(vehicleName);
            return ResponseEntity.ok(Map.of("status", "success", "data", vehicles));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", "Failed to search vehicles", "error", e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchVehicles(@RequestParam String searchTerm) {
        try {
            List<Vehicle> vehicles = vehicleService.searchVehicles(searchTerm);
            return ResponseEntity.ok(Map.of("status", "success", "data", vehicles));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("status", "error", "message", "Failed to search vehicles", "error", e.getMessage()));
        }
    }
}
