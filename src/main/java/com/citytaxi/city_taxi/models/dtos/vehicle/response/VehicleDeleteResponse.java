package com.citytaxi.city_taxi.models.dtos.vehicle.response;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@Getter
public class VehicleDeleteResponse {
    private Long id;
    private String manufacturer;
    private String model;
    private String colour;
    private String licensePlate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
