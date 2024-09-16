package com.citytaxi.city_taxi.controllers;

import com.citytaxi.city_taxi.models.dtos.vehicle_type.request.VehicleTypeCreateRequest;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.request.VehicleTypeUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.response.VehicleTypeGetResponse;
import com.citytaxi.city_taxi.services.IVehicleTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/vehicle/type")
public class VehicleTypeController {
    private final IVehicleTypeService vehicleTypeService;

    @GetMapping
    public ResponseEntity<?> getVehicleTypes(@RequestParam(value = "id", required = false) Long id) {
        final List<VehicleTypeGetResponse> vehicleTypes = vehicleTypeService.getVehicleTypes(id);

        if (vehicleTypes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(vehicleTypes);
    }

    @PostMapping
    public ResponseEntity<?> createVehicleType(@Valid @RequestBody List<VehicleTypeCreateRequest> payload) {
        return new ResponseEntity<>(vehicleTypeService.create(payload), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<?> updateVehicleType(@Valid @RequestBody List<VehicleTypeUpdateRequest> payload) {
        return new ResponseEntity<>(vehicleTypeService.update(payload), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteVehicleType(@RequestParam("ids") List<Long> ids) {
        return new ResponseEntity<>(vehicleTypeService.delete(ids), HttpStatus.OK);
    }
}
