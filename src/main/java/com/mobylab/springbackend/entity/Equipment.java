package com.mobylab.springbackend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "equipment", schema = "project")

public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipment_seq")
    @SequenceGenerator(name = "equipment_seq", sequenceName = "project.equipment_seq", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    private String name;
    private String description;
    private double pricePerDay;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "equipment")
    private List<Rental> rentals;

    @OneToMany(mappedBy = "equipment")
    private List<Review> reviews;

    // getters & setters
    public Long getId() {
        return id;
    }
    public Equipment setId(Long id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }
    public Equipment setName(String name) {
        this.name = name;
        return this;
    }
    public String getDescription() {
        return description;
    }
    public Equipment setDescription(String description) {
        this.description = description;
        return this;
    }
    public double getPricePerDay() {
        return pricePerDay;
    }
    public Equipment setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
        return this;
    }
    public Category getCategory() {
        return category;
    }
    public Equipment setCategory(Category category) {
        this.category = category;
        return this;
    }
    public List<Rental> getRentals() {
        return rentals;
    }
    public Equipment setRentals(List<Rental> rentals) {
        this.rentals = rentals;
        return this;
    }
    public List<Review> getReviews() {
        return reviews;
    }
    public Equipment setReviews(List<Review> reviews) {
        this.reviews = reviews;
        return this;
    }
    public Equipment addRental(Rental rental) {
        this.rentals.add(rental);
        rental.setEquipment(this);
        return this;
    }
    public Equipment removeRental(Rental rental) {
        this.rentals.remove(rental);
        rental.setEquipment(null);
        return this;
    }
}
