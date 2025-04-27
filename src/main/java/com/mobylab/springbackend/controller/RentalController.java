package com.mobylab.springbackend.controller;

import com.mobylab.springbackend.service.dto.RentalRequestDTO;
import com.mobylab.springbackend.service.dto.RentalResponseDTO;
import com.mobylab.springbackend.service.RentalService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
@Profile("backend")
@RestController
@EnableMethodSecurity
@RequestMapping("/api/v1/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    @SecurityRequirement(name = "Bearer Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rental created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid rental request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Access denied (not CLIENT)")
    })
    public ResponseEntity<RentalResponseDTO> create(@RequestBody RentalRequestDTO dto) {
        return ResponseEntity.ok(rentalService.create(dto));
    }
}
