package com.citytaxi.city_taxi.controllers;

import com.citytaxi.city_taxi.services.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminController {
    private final IAdminService adminService;

    /**
     * Handles the HTTP GET request for retrieving dashboard data.
     *
     * @return ResponseEntity containing the dashboard data.
     */
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardData() {
        return ResponseEntity.ok(adminService.getDashboardData());
    }

}
