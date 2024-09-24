package com.citytaxi.city_taxi.models.dtos.payment.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentCreateRequest {
    @NotNull(message = "Cost is required")
    @DecimalMin(value = "0.0", message = "Cost must be greater than 0")
    private Double cost;
    @NotNull(message = "Booking id is required")
    @Min(value = 1, message = "Booking id must be greater than 0")
    private Long bookingId;
}
