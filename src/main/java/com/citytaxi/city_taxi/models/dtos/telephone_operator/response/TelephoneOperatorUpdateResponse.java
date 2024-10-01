package com.citytaxi.city_taxi.models.dtos.telephone_operator.response;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@Getter
public class TelephoneOperatorUpdateResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
