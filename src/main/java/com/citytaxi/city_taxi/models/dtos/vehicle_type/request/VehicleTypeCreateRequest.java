package com.citytaxi.city_taxi.models.dtos.vehicle_type.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleTypeCreateRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotNull(message = "Price per meter is required")
    @Min(value = 1, message = "Price per meter must be greater than 0")
    private Double pricePerMeter;
}
