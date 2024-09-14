package com.citytaxi.city_taxi.models.dtos.account.request;

import com.citytaxi.city_taxi.models.enums.EAccountStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountUpdateRequest {
    @NotNull(message = "Id is required")
    private Long id;
    private String username;
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$",
            message = "Password must contain at least one lowercase letter, one uppercase letter, " +
                    "one digit, one special character, and must be at least 8 characters long"
    )
    private String password;
    private EAccountStatus status;
}
