package com.citytaxi.city_taxi.models.dtos.rating.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RatingGetResponse {
    private Long id;
    private Integer rating;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
