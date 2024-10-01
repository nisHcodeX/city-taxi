package com.citytaxi.city_taxi.models.dtos.driver.request;

import com.citytaxi.city_taxi.models.enums.EDriverAvailabilityStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class DriverUpdateRequest {
    @NotNull(message = "Id is required")
    @Min(1)
    private Long id;
    private String name;
    @Pattern(regexp = "^(.+)@(.+)$", message = "Invalid email")
    private String email;
    @Pattern(regexp = "^\\+94\\d{9}$", message = "Invalid phone number")
    private String phoneNumber;
    private String driverLicense;
    private EDriverAvailabilityStatus availability;
    private Double latitude;
    private Double longitude;
    private String locationName;
}
