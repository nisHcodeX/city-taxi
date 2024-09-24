package com.citytaxi.city_taxi.models.dtos.payment.response;

import com.citytaxi.city_taxi.models.enums.EPaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentGetResponse {
    private Long id;
    private Double cost;
    private EPaymentType type;
    private OffsetDateTime createdAt;
}
