package com.mobylab.springbackend.controller;

import com.mobylab.springbackend.service.dto.EquipmentRequestDTO;
import com.mobylab.springbackend.service.dto.EquipmentResponseDTO;
import com.mobylab.springbackend.service.EquipmentService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableMethodSecurity
@RequestMapping("/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched all equipment"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public List<EquipmentResponseDTO> getAll() {
        return equipmentService.getAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fetched equipment by ID"),
            @ApiResponse(responseCode = "404", description = "Equipment not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public EquipmentResponseDTO getById(@PathVariable Long id) {
        return equipmentService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('CLIENT')")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied (not admin)")
    })
    public ResponseEntity<EquipmentResponseDTO> create(@RequestBody EquipmentRequestDTO dto) {
        return ResponseEntity.ok(equipmentService.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Equipment updated"),
            @ApiResponse(responseCode = "404", description = "Equipment not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied (not admin)")
    })
    public ResponseEntity<EquipmentResponseDTO> update(@PathVariable Long id, @RequestBody EquipmentRequestDTO dto) {
        return ResponseEntity.ok(equipmentService.update(id, dto));
    }

    //send mail for put mapping


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Equipment deleted"),
            @ApiResponse(responseCode = "404", description = "Equipment not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied (not admin)")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        equipmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
