package com.mobylab.springbackend.service;


import com.mobylab.springbackend.entity.Category;
import com.mobylab.springbackend.entity.Equipment;
import com.mobylab.springbackend.repository.CategoryRepository;
import com.mobylab.springbackend.repository.EquipmentRepository;
import com.mobylab.springbackend.service.dto.EquipmentRequestDTO;
import com.mobylab.springbackend.service.dto.EquipmentResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final CategoryRepository categoryRepository;
    @Autowired
    private MailService mailService;

    public EquipmentService(EquipmentRepository equipmentRepository, CategoryRepository categoryRepository) {
        this.equipmentRepository = equipmentRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<EquipmentResponseDTO> getAll() {
        return equipmentRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public EquipmentResponseDTO getById(Long id) {
        Equipment eq = equipmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Equipment not found"));
        return mapToDTO(eq);
    }

    public EquipmentResponseDTO create(EquipmentRequestDTO dto) {
        Category cat = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Equipment eq = new Equipment();
        eq.setName(dto.getName());
        eq.setDescription(dto.getDescription());
        eq.setPricePerDay(dto.getPricePerDay());
        eq.setCategory(cat);

        Equipment saved = equipmentRepository.save(eq);

        // 🧠 Ia emailul utilizatorului logat
        String userEmail = SecurityUtil.getCurrentUserEmail();

        // 📨 Trimite email
        mailService.sendMail(
                userEmail,
                "Echipament adăugat cu succes!",
                "<h2>Ai adăugat un echipament:</h2>" +
                        "<ul>" +
                        "<li><b>Nume:</b> " + saved.getName() + "</li>" +
                        "<li><b>Descriere:</b> " + saved.getDescription() + "</li>" +
                        "<li><b>Preț/zi:</b> " + saved.getPricePerDay() + " RON</li>" +
                        "<li><b>Categorie:</b> " + saved.getCategory().getName() + "</li>" +
                        "</ul>"
        );

        return mapToDTO(saved);
    }


    public EquipmentResponseDTO update(Long id, EquipmentRequestDTO dto) {
        Equipment eq = equipmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Equipment not found"));
        Category cat = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() -> new EntityNotFoundException("Category not found"));

        eq.setName(dto.getName());
        eq.setDescription(dto.getDescription());
        eq.setPricePerDay(dto.getPricePerDay());
        eq.setCategory(cat);

        return mapToDTO(equipmentRepository.save(eq));
    }

    public void delete(Long id) {
        if (!equipmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Equipment not found");
        }
        equipmentRepository.deleteById(id);
    }

    private EquipmentResponseDTO mapToDTO(Equipment eq) {
        EquipmentResponseDTO dto = new EquipmentResponseDTO();
        dto.setId(eq.getId());
        dto.setName(eq.getName());
        dto.setDescription(eq.getDescription());
        dto.setPricePerDay(eq.getPricePerDay());
        dto.setCategoryName(eq.getCategory().getName());
        return dto;
    }
}