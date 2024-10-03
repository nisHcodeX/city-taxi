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

    /**
     * Retrieves a list of vehicles. If an ID is provided, retrieves the vehicle with the specified ID.
     *
     * @param id Optional vehicle ID to retrieve a specific vehicle.
     * @return ResponseEntity containing the list of VehicleGetResponse objects or NO_CONTENT status if no vehicles are found.
     */
    @GetMapping
    public ResponseEntity<?> getVehicles(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "driverid", required = false) Long driverId
    ) {
        final List<VehicleGetResponse> vehicles = vehicleService.getVehicles(id, driverId);

        if (vehicles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Creates a list of vehicles based on the provided payload.
     *
     * @param payload List of VehicleCreateRequest objects containing vehicle details.
     * @return ResponseEntity containing the list of created VehicleCreateResponse objects with CREATED status.
     */
    @PostMapping
    public ResponseEntity<?> createVehicle(@Valid @RequestBody List<VehicleCreateRequest> payload) {
        return new ResponseEntity<>(vehicleService.create(payload), HttpStatus.CREATED);
    }

    /**
     * Updates a list of vehicles based on the provided payload.
     *
     * @param payload List of VehicleUpdateRequest objects containing updated vehicle details.
     * @return ResponseEntity containing the list of updated VehicleUpdateResponse objects with OK status.
     */
    @PatchMapping
    public ResponseEntity<?> updateVehicle(@Valid @RequestBody List<VehicleUpdateRequest> payload) {
        return new ResponseEntity<>(vehicleService.update(payload), HttpStatus.OK);
    }

    /**
     * Deletes a list of vehicles based on the provided IDs.
     *
     * @param ids List of vehicle IDs to be deleted.
     * @return ResponseEntity containing the list of deleted VehicleDeleteResponse objects with OK status.
     */
    @DeleteMapping
    public ResponseEntity<?> deleteVehicle(@RequestParam("ids") List<Long> ids) {
        return new ResponseEntity<>(vehicleService.delete(ids), HttpStatus.OK);
    }
}
