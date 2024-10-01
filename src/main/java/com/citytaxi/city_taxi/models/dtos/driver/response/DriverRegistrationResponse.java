package com.citytaxi.city_taxi.models.dtos.driver.response;

import com.citytaxi.city_taxi.models.enums.EAccountStatus;
import com.citytaxi.city_taxi.models.enums.EDriverAvailabilityStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@Getter
public class DriverRegistrationResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String driverLicense;
    private String username;
    private String password;
    private EDriverAvailabilityStatus availability;
    private Double latitude;
    private Double longitude;
    private String locationName;
    private EAccountStatus status;
    private OffsetDateTime createdAt;
}
