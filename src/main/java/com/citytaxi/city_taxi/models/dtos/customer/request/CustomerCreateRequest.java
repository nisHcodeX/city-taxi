package com.citytaxi.city_taxi.models.dtos.customer.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerCreateRequest {
    @NotBlank
    private String name;
    @Pattern(regexp = "^(.+)@(.+)$", message = "Invalid email")
    private String email;
    @NotBlank(message = "Valid phone number is required")
    @Pattern(regexp = "^\\+94\\d{9}$", message = "Invalid phone number")
    private String phoneNumber;
}
