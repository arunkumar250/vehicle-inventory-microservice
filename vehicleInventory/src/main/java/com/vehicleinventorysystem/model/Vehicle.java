package com.vehicleinventorysystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int vehicleId;

    @Column(name = "model",nullable = false, unique = true)
    private String model;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "status", nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'available'")
    private String status;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;
    
    @Column(name = "available_count")
    private int availableCount;
    
    @Column(name = "engine_capacity", nullable = false)
    private int engineCapacity;

    public int getEngineCapacity() {
		return engineCapacity;
	}

	public void setEngineCapacity(int engineCapacity) {
		this.engineCapacity = engineCapacity;
	}

    @Override
    public String toString() {
        return "Vehicle [vehicleId=" + vehicleId + ", model=" + model + ", price=" + price + ", status=" + status
                + ", createdAt=" + createdAt + ", availableCount=" + availableCount + ", engineCapacity=" + engineCapacity
                + ", brand=" + brand + "]";
    }

	public int getAvailableCount() {
		return availableCount;
	}

	public void setAvailableCount(int availableCount) {
		this.availableCount = availableCount;
	}

	@ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    // Getters and Setters
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
