package com.mobylab.springbackend.service.dto;


import java.time.LocalDate;

public class RentalRequestDTO {
    private Long equipmentId;
    private LocalDate startDate;
    private LocalDate endDate;

    // getters & setters

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
