package com.mobylab.springbackend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq", sequenceName = "project.category_seq", allocationSize = 1)
    private Long id;


    private String name;

    @OneToMany(mappedBy = "category")
    private List<Equipment> equipmentList;

    // getters & setters
    public Long getId() {
        return id;
    }
    public Category setId(Long id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }
    public Category setName(String name) {
        this.name = name;
        return this;
    }
    public List<Equipment> getEquipmentList() {
        return equipmentList;
    }
    public Category setEquipmentList(List<Equipment> equipmentList) {
        this.equipmentList = equipmentList;
        return this;
    }

}
