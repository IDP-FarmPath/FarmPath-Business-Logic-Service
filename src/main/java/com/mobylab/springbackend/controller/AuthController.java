package com.mobylab.springbackend.controller;

import com.mobylab.springbackend.service.AuthService;
import com.mobylab.springbackend.service.dto.LoginDto;
import com.mobylab.springbackend.service.dto.LoginResponseDto;
import com.mobylab.springbackend.service.dto.RegisterDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private LoginResponseDto loginResponseDto;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Email already used"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> register(@RequestBody RegisterDto registerDto) {
        logger.info("Request to register user {}", registerDto.getEmail());
        authService.register(registerDto);
        logger.info("Successfully registered user {}", registerDto.getEmail());
        return new ResponseEntity<>("User registered", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Wrong credentials"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        logger.info("Request to login for user {}", loginDto.getEmail());
        String token = authService.login(loginDto);
        logger.info("Successfully logged in user {}", loginDto.getEmail());
        return new ResponseEntity<>(loginResponseDto.setToken(token), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/token")
    @RolesAllowed({"CLIENT", "ADMIN"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token is valid"),
            @ApiResponse(responseCode = "403", description = "Access denied"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - missing/invalid token")
    })
    public ResponseEntity<?> validateToken() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        logger.info("Request to validate token for user {}", user.getUsername());
        String email = user.getUsername();
        logger.info("Successfully validated token for user {}", user.getUsername());
        return new ResponseEntity<>(email, HttpStatus.OK);
    }
}
