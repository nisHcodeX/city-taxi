package com.citytaxi.city_taxi.controllers;

import com.citytaxi.city_taxi.models.dtos.driver.request.DriverCreateRequest;
import com.citytaxi.city_taxi.models.dtos.driver.request.DriverUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.driver.response.DriverGetResponse;
import com.citytaxi.city_taxi.services.IDriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/driver")
public class DriverController {
    private final IDriverService driverService;

    /**
     * Retrieves a list of drivers. If an ID is provided, retrieves the driver with the specified ID.
     *
     * @param id Optional driver ID to retrieve a specific driver.
     * @return ResponseEntity containing a list of DriverGetResponse objects or NO_CONTENT status if no drivers are found.
     */
    @GetMapping
    public ResponseEntity<?> getDrivers(@RequestParam(value = "id", required = false) Long id) {
        final List<DriverGetResponse> drivers = driverService.getDrivers(id);

        if (drivers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(drivers);
    }

    /**
     * Registers a new driver based on the provided payload.
     *
     * @param payload DriverCreateRequest object containing driver details.
     * @return ResponseEntity containing the registered driver details and CREATED status.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerDriver(@Valid @RequestBody DriverCreateRequest payload) {
        return new ResponseEntity<>(driverService.registerDriver(payload), HttpStatus.CREATED);
    }

    /**
     * Updates a list of drivers based on the provided payload.
     *
     * @param payload List of DriverUpdateRequest objects containing updated driver details.
     * @return ResponseEntity containing the updated driver details and OK status.
     */
    @PatchMapping
    public ResponseEntity<?> updateDriver(@Valid @RequestBody List<DriverUpdateRequest> payload) {
        return new ResponseEntity<>(driverService.update(payload), HttpStatus.OK);
    }

    /**
     * Deletes a list of drivers based on the provided IDs.
     *
     * @param ids List of driver IDs to be deleted.
     * @return ResponseEntity containing the deleted driver details and OK status.
     */
    @DeleteMapping
    public ResponseEntity<?> deleteDriver(@RequestParam("ids") List<Long> ids) {
        return new ResponseEntity<>(driverService.delete(ids), HttpStatus.OK);
    }
}
