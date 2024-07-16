package com.vehicleinventorysystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vehicleinventorysystem.model.Brand;
import com.vehicleinventorysystem.model.Vehicle;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    List<Vehicle> findByBrandAndStatus(Brand brand, String status);
    List<Vehicle> findByBrand_BrandName(String brandName);
    List<Vehicle> findByModelContainingIgnoreCase(String name);
    
    @Query("SELECT v FROM Vehicle v " +
            "WHERE LOWER(v.model) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(v.status) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(CAST(v.price AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(CAST(v.createdAt AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(CAST(v.availableCount AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(CAST(v.engineCapacity AS string)) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Vehicle> searchVehicles(String searchTerm);
}
