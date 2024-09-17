package com.citytaxi.city_taxi.models.dtos.booking.response;

import com.citytaxi.city_taxi.models.dtos.customer.response.CustomerGetResponse;
import com.citytaxi.city_taxi.models.dtos.driver.response.DriverGetResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle.response.VehicleGetResponse;
import com.citytaxi.city_taxi.models.enums.EBookingStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingGetResponse {
    private Long id;
    private Double estimatedCost;
    private Double distanceInMeters;
    private EBookingStatus status;
    private DriverGetResponse driver;
    private CustomerGetResponse customer;
    private VehicleGetResponse vehicle;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
