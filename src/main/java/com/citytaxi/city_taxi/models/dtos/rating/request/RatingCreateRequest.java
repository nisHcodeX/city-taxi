package com.citytaxi.city_taxi.models.dtos.rating.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingCreateRequest {
    @NotNull(message = "customerId is required")
    @Min(value = 1, message = "customerId must be greater than 0")
    private Long customerId;
    @NotNull(message = "bookingId is required")
    @Min(value = 1, message = "bookingId must be greater than 0")
    private Long bookingId;
    @NotNull(message = "rating is required")
    @Min(value = 1, message = "rating must be greater than 0")
    @Max(value = 5, message = "rating must be less than 6")
    private Integer rating;
}
