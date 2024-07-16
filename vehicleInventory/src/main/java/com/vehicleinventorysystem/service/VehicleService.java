package com.vehicleinventorysystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vehicleinventorysystem.Repository.VehicleRepository;
import com.vehicleinventorysystem.model.Brand;
import com.vehicleinventorysystem.model.Vehicle;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BrandService brandService;

    @Transactional(readOnly = true)
    public List<Vehicle> getAllVehicles() throws Exception {
        try {
            return vehicleRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Error fetching vehicles: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Vehicle getVehicleById(int id) throws Exception {
        try {
            return vehicleRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new Exception("Error fetching vehicle: " + e.getMessage());
        }
    }

    @Transactional
    public Vehicle saveVehicle(Vehicle vehicle) throws Exception {
        try {
            return vehicleRepository.save(vehicle);
        } catch (Exception e) {
            throw new Exception("Error saving vehicle: " + e.getMessage());
        }
    }

    @Transactional
    public Vehicle updateVehicle(int id, Vehicle vehicle) throws Exception {
        try {
            if (vehicleRepository.findById(id).isPresent()) {
                vehicle.setVehicleId(id);
                return vehicleRepository.save(vehicle);
            }
            return null;
        } catch (Exception e) {
            throw new Exception("Error updating vehicle: " + e.getMessage());
        }
    }

    @Transactional
    public String deleteVehicle(int id) throws Exception {
        try {
            Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
            if (optionalVehicle.isPresent()) {
                vehicleRepository.deleteById(id);
                return "Vehicle with ID " + id + " deleted successfully";
            }
            return "Vehicle with ID " + id + " not found";
        } catch (Exception e) {
            throw new Exception("Error deleting vehicle: " + e.getMessage());
        }
    }

    @Transactional
    public String requestVehicles(String brandName, int quantity) throws Exception {
        try {
            Brand brand = brandService.getBrandByName(brandName);
            if (brand == null) {
                return "Brand not found";
            }
            List<Vehicle> availableVehicles = vehicleRepository.findByBrandAndStatus(brand, "available");
            if (availableVehicles.size() < quantity) {
                return "Not enough vehicles available";
            }
            for (int i = 0; i < quantity; i++) {
                Vehicle vehicle = availableVehicles.get(i);
                vehicle.setStatus("sold");
                vehicleRepository.save(vehicle);
            }
            brand.setAvailableModelsCount(brand.getAvailableModelsCount() - quantity);
            brandService.updateBrand(brand.getBrandId(), brand);
            return "Requested vehicles are reserved for you";
        } catch (Exception e) {
            throw new Exception("Error requesting vehicles: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<Vehicle> findVehiclesByBrandName(String brandName) throws Exception {
        try {
            return vehicleRepository.findByBrand_BrandName(brandName);
        } catch (Exception e) {
            throw new Exception("Error fetching vehicles by brand name: " + e.getMessage());
        }
    }
    
    @Transactional(readOnly = true)
    public List<Vehicle> searchVehicleByName(String name) throws Exception {
        try {
            return vehicleRepository.findByModelContainingIgnoreCase(name);
        } catch (Exception e) {
            throw new Exception("Error searching vehicles: " + e.getMessage());
        }
    }
    
    @Transactional(readOnly = true)
    public List<Vehicle> searchVehicles(String searchTerm) throws Exception {
        try {
            return vehicleRepository.searchVehicles(searchTerm);
        } catch (Exception e) {
            throw new Exception("Error searching vehicles: " + e.getMessage());
        }
    }
}
