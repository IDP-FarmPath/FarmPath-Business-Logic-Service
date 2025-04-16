package com.mobylab.springbackend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Rental {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    // getters & setters
    public Long getId() {
        return id;
    }
    public Rental setId(Long id) {
        this.id = id;
        return this;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public Rental setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public Rental setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }
    public User getUser() {
        return user;
    }
    public Rental setUser(User user) {
        this.user = user;
        return this;
    }
    public Equipment getEquipment() {
        return equipment;
    }


}
