package com.citytaxi.city_taxi.models.dtos.vehicle_type.response;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@Getter
public class VehicleTypeDeleteResponse {
    private Long id;
    private String name;
    private Double pricePerMeter;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
