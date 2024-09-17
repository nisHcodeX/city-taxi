package com.citytaxi.city_taxi.controllers;

import com.citytaxi.city_taxi.models.dtos.vehicle.request.VehicleCreateRequest;
import com.citytaxi.city_taxi.models.dtos.vehicle.request.VehicleUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.vehicle.response.VehicleGetResponse;
import com.citytaxi.city_taxi.services.IVehicleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vehicle")
public class VehicleController {
    private final IVehicleService vehicleService;

    @GetMapping
    public ResponseEntity<?> getVehicles(@RequestParam(value = "id", required = false) Long id) {
        final List<VehicleGetResponse> vehicles = vehicleService.getVehicles(id);

        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(vehicles);
    }

    @PostMapping
    public ResponseEntity<?> createVehicle(@Valid @RequestBody List<VehicleCreateRequest> payload) {
        return new ResponseEntity<>(vehicleService.create(payload), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<?> updateVehicle(@Valid @RequestBody List<VehicleUpdateRequest> payload) {
        return new ResponseEntity<>(vehicleService.update(payload), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteVehicle(@RequestParam("ids") List<Long> ids) {
        return new ResponseEntity<>(vehicleService.delete(ids), HttpStatus.OK);
    }
}
