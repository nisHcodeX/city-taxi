package com.citytaxi.city_taxi.models.dtos.customer.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CustomerCreateRequest {
    private String name;
    @NotNull(message = "Email is required")
    @Pattern(regexp = "^(.+)@(.+)$", message = "Invalid email")
    private String email;
    @NotNull(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number")
    private String phoneNumber;
}
