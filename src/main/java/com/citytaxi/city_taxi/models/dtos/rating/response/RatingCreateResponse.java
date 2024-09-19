package com.citytaxi.city_taxi.models.dtos.rating.response;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@Getter
public class RatingCreateResponse {
    private Long id;
    private Integer rating;
    private OffsetDateTime createdAt;
}
