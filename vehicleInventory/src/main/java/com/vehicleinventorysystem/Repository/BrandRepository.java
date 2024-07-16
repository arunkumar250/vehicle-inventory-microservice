package com.vehicleinventorysystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vehicleinventorysystem.model.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    Brand findByBrandName(String brandName);
    
    @Query("SELECT b FROM Brand b " +
            "WHERE LOWER(b.brandName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(b.type) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR b.availableModelsCount = :searchCount")
    List<Brand> searchBrands(String searchTerm, int searchCount);
}
