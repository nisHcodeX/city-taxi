package com.citytaxi.city_taxi.models.dtos.account.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountCreateRequest {
    @NotBlank(message = "Valid username is required")
    private String username;
    @NotBlank(message = "Valid password is required")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, " +
                    "one digit, one special character, and must be at least 8 characters long"
    )
    private String password;
    @NotNull(message = "Valid customer id is required")
    @Min(value = 1, message = "Valid customer id is required")
    private Long customerId;
}
