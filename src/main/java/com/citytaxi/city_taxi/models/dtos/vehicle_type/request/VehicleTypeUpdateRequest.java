package com.citytaxi.city_taxi.models.dtos.vehicle_type.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleTypeUpdateRequest {
    @NotNull(message = "Id must not be null")
    private Long id;
    private String name;
    @Min(value = 1, message = "Price per meter must be greater than 0")
    private Double pricePerMeter;
}
