package com.vehicleinventorysystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicleinventorysystem.Repository.BrandRepository;
import com.vehicleinventorysystem.model.Brand;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public List<Brand> getAllBrands() throws Exception {
        try {
            return brandRepository.findAll();
        } catch (Exception e) {
            throw new Exception("Error fetching brands: " + e.getMessage());
        }
    }

    public Brand getBrandById(int id) throws Exception {
        try {
            Optional<Brand> brand = brandRepository.findById(id);
            return brand.orElse(null);
        } catch (Exception e) {
            throw new Exception("Error fetching brand: " + e.getMessage());
        }
    }

    public Map<String, String> saveBrand(Brand brand) {
        Map<String, String> response = new HashMap<>();
        try {
            brandRepository.save(brand);
            response.put("status", "success");
            response.put("message", "Brand added successfully");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Brand not able to be added");
            response.put("reason", e.getMessage());
        }
        return response;
    }

    public Map<String, String> updateBrand(int id, Brand brand) {
        Map<String, String> response = new HashMap<>();
        try {
            if (brandRepository.existsById(id)) {
                brand.setBrandId(id);
                brandRepository.save(brand);
                response.put("status", "success");
                response.put("message", "Brand updated successfully");
            } else {
                response.put("status", "error");
                response.put("message", "Brand with ID " + id + " not found");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Brand not able to be updated");
            response.put("reason", e.getMessage());
        }
        return response;
    }

    public Map<String, String> deleteBrand(int id) {
        Map<String, String> response = new HashMap<>();
        try {
            if (brandRepository.existsById(id)) {
                brandRepository.deleteById(id);
                response.put("status", "success");
                response.put("message", "Brand with ID " + id + " deleted successfully");
            } else {
                response.put("status", "error");
                response.put("message", "Brand with ID " + id + " not found");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Brand not able to be deleted");
            response.put("reason", e.getMessage());
        }
        return response;
    }

    public Brand getBrandByName(String brandName) throws Exception {
        try {
            return brandRepository.findByBrandName(brandName);
        } catch (Exception e) {
            throw new Exception("Error fetching brand: " + e.getMessage());
        }
    }
    

    public List<Brand> searchBrands(String searchTerm) throws Exception {
        try {
            int count = 0;
            // Check if the search term can be parsed as an integer (for availableModelsCount)
            try {
                count = Integer.parseInt(searchTerm);
            } catch (NumberFormatException ignored) {}

            // Search using manual query
            return brandRepository.searchBrands(searchTerm, count);
        } catch (Exception e) {
            throw new Exception("Error searching brands: " + e.getMessage());
        }
    }
}
