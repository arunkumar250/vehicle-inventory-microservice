package com.vehicleinventorysystem.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int brandId;

    @Column(name = "brand_name", nullable = false, unique = true)
    private String brandName;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "available_models_count", nullable = false)
    private int availableModelsCount;
    
//    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
//    private Set<Vehicle> vehicles;

    // Getters and Setters
    public int getBrandId() {
        return brandId;
    }

    public int getAvailableModelsCount() {
		return availableModelsCount;
	}

	public void setAvailableModelsCount(int availableModelsCount) {
		this.availableModelsCount = availableModelsCount;
	}

	public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
