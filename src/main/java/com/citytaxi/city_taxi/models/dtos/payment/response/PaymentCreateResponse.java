package com.citytaxi.city_taxi.models.dtos.payment.response;

import com.citytaxi.city_taxi.models.enums.EPaymentType;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@Getter
public class PaymentCreateResponse {
    private Long id;
    private Double cost;
    private EPaymentType type;
    private OffsetDateTime createdAt;
}
