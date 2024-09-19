package com.citytaxi.city_taxi.models.dtos.vehicle.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleUpdateRequest {
    @NotNull(message = "Id is required")
    @Min(1)
    private Long id;
    private String model;
    private String manufacturer;
    private String colour;
    private String licensePlate;
    @Min(value = 1, message = "Vehicle type id must be greater than 0")
    private Long vehicleTypeId;
}
