package com.citytaxi.city_taxi.controllers;

import com.citytaxi.city_taxi.models.dtos.login.request.LoginRequest;
import com.citytaxi.city_taxi.services.ILoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class LoginController {
    private final ILoginService loginService;

    /**
     * Handles the login request for customers and drivers.
     *
     * @param payload The LoginRequest object containing the login details.
     * @return A ResponseEntity containing the login response.
     */
    @PostMapping("/public/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest payload) {
        return ResponseEntity.ok(loginService.customerAndDriverLogin(payload));
    }
}
