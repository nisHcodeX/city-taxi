package com.citytaxi.city_taxi.models.dtos.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDetailsSMSResponse {
    private String manufacturer;
    private String model;
    private String colour;
    private String licensePlate;
    private String vehicleType;
}
