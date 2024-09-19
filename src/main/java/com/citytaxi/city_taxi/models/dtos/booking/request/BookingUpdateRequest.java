package com.citytaxi.city_taxi.models.dtos.booking.request;

import com.citytaxi.city_taxi.models.enums.EBookingStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingUpdateRequest {
    @NotNull(message = "id is required")
    @Min(value = 1, message = "id must be greater than 0")
    private Long id;
    private EBookingStatus status;
}
