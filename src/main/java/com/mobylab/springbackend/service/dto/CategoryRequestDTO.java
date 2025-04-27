package com.mobylab.springbackend.service.dto;

import org.springframework.context.annotation.Profile;

@Profile("backend")
public class CategoryRequestDTO {
    private String name;

    // Getters & setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
