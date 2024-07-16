package com.vehicleinventorysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vehicleinventorysystem.model.Brand;
import com.vehicleinventorysystem.service.BrandService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/getallbrands")
    public ResponseEntity<?> getAllBrands() {
        try {
            List<Brand> brands = brandService.getAllBrands();
            return ResponseEntity.ok(brands);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch brands", "reason", e.getMessage()));
        }
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable int id) {
        try {
            Brand brand = brandService.getBrandById(id);
            if (brand != null) {
                return ResponseEntity.ok(brand);
            }
            return ResponseEntity.status(404).body(Map.of("error", "Brand not found"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch brand", "reason", e.getMessage()));
        }
    }

    @PostMapping("/addbrand")
    public ResponseEntity<?> createBrand(@RequestBody Brand brand) {
        try {
            Map<String, String> result = brandService.saveBrand(brand);
            if ("success".equals(result.get("status"))) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.badRequest().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to add brand", "reason", e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable int id, @RequestBody Brand brand) {
        try {
            Map<String, String> result = brandService.updateBrand(id, brand);
            if ("success".equals(result.get("status"))) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.status(404).body(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to update brand", "reason", e.getMessage()));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable int id) {
        try {
            Map<String, String> result = brandService.deleteBrand(id);
            if ("success".equals(result.get("status"))) {
                return ResponseEntity.ok(result);
            }
            return ResponseEntity.badRequest().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to delete brand", "reason", e.getMessage()));
        }
    }

    @GetMapping("/getbrandbyname/{brandName}")
    public ResponseEntity<?> getBrandByName(@PathVariable String brandName) {
        try {
            Brand brand = brandService.getBrandByName(brandName);
            if (brand != null) {
                return ResponseEntity.ok(brand);
            }
            return ResponseEntity.status(404).body(Map.of("error", "Brand not found"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to fetch brand", "reason", e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<?> searchBrands(@RequestParam String searchTerm) {
        try {
            List<Brand> brands = brandService.searchBrands(searchTerm);
            return ResponseEntity.ok(brands);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to search brands", "reason", e.getMessage()));
        }
    }
}
