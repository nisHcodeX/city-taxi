package com.citytaxi.city_taxi.models.dtos.booking.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingCreateRequest {
    @NotNull(message = "customerId is required")
    @Min(value = 1, message = "customerId must be greater than 0")
    private Long customerId;
    @NotNull(message = "driverId is required")
    @Min(value = 1, message = "customerId must be greater than 0")
    private Long driverId;
    @NotNull(message = "startLatitude is required")
    private Double startLatitude;
    @NotNull(message = "startLongitude is required")
    private Double startLongitude;
    @NotNull(message = "destLatitude is required")
    private Double destLatitude;
    @NotNull(message = "destLongitude is required")
    private Double destLongitude;
}
