package com.mobylab.springbackend.service.dto;
public class EquipmentRequestDTO {
    private String name;
    private String description;
    private double pricePerDay;
    private Long categoryId;

    // getters & setters
    public String getName() {
        return name;
    }
    public EquipmentRequestDTO setName(String name) {
        this.name = name;
        return this;
    }
    public String getDescription() {
        return description;
    }
    public EquipmentRequestDTO setDescription(String description) {
        this.description = description;
        return this;
    }
    public double getPricePerDay() {
        return pricePerDay;
    }
    public EquipmentRequestDTO setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
        return this;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
