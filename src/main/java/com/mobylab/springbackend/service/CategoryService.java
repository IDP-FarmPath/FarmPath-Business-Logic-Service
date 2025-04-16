package com.mobylab.springbackend.service;


import com.mobylab.springbackend.entity.Category;
import com.mobylab.springbackend.repository.CategoryRepository;
import com.mobylab.springbackend.service.dto.CategoryRequestDTO;
import com.mobylab.springbackend.service.dto.CategoryResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponseDTO> getAll() {
        return categoryRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CategoryResponseDTO getById(Long id) {
        Category cat = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return mapToDTO(cat);
    }

    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        Category cat = new Category();
        cat.setName(dto.getName());
        return mapToDTO(categoryRepository.save(cat));
    }

    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        Category cat = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        cat.setName(dto.getName());
        return mapToDTO(categoryRepository.save(cat));
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }

    private CategoryResponseDTO mapToDTO(Category cat) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(cat.getId());
        dto.setName(cat.getName());
        return dto;
    }
}