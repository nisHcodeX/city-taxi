package com.citytaxi.city_taxi.models.dtos.vehicle_type.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleTypeGetResponse {
    private Long id;
    private String name;
    private Double pricePerMeter;
    private Integer seatCount;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
