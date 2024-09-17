package com.citytaxi.city_taxi.models.dtos.vehicle.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleCreateRequest {
    @NotNull(message = "Driver id is required")
    @Min(value = 1, message = "Driver id must be greater than 0")
    private Long driverId;
    @NotBlank(message = "Model is required")
    private String model;
    @NotBlank(message = "Manufacturer is required")
    private String manufacturer;
    @NotBlank(message = "Colour is required")
    private String colour;
    @NotBlank(message = "License plate is required")
    private String licensePlate;
    @NotNull
    @Min(value = 1, message = "Vehicle type id must be greater than 0")
    private Long vehicleTypeId;
}
