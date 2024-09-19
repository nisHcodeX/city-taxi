package com.citytaxi.city_taxi.models.dtos.rating.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingUpdateRequest {
    @NotNull(message = "id is required")
    @Min(value = 1, message = "id must be greater than 0")
    private Long id;
    @NotNull(message = "rating is required")
    @Min(value = 1, message = "rating must be greater than 0")
    @Max(value = 5, message = "rating must be less than 6")
    private Integer rating;
}
