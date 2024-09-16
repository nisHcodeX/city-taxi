package com.citytaxi.city_taxi.models.dtos.customer.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerRegistrationRequest {
    @NotBlank
    private String name;
    @NotBlank(message = "Valid email is required")
    @Pattern(regexp = "^(.+)@(.+)$", message = "Invalid email")
    private String email;
    @NotBlank(message = "Valid phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Invalid phone number")
    private String phoneNumber;
    @NotBlank(message = "Valid username is required")
    private String username;
    @NotBlank(message = "Valid password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, " +
                    "one digit, one special character, and must be at least 8 characters long"
    )
    private String password;
}
