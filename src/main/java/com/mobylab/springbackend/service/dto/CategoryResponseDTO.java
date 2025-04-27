package com.mobylab.springbackend.service.dto;

import org.springframework.context.annotation.Profile;

@Profile("backend")
public class CategoryResponseDTO {
    private Long id;
    private String name;

    // Getters & setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}