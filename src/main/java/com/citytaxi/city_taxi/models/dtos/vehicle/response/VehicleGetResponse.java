package com.citytaxi.city_taxi.models.dtos.vehicle.response;

import com.citytaxi.city_taxi.models.dtos.vehicle_type.response.VehicleTypeGetResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleGetResponse {
    private Long id;
    private String manufacturer;
    private String model;
    private String colour;
    private String licensePlate;
    private VehicleTypeGetResponse vehicleType;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
