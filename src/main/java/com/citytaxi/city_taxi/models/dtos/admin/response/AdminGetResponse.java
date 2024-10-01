package com.citytaxi.city_taxi.models.dtos.admin.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminGetResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

}