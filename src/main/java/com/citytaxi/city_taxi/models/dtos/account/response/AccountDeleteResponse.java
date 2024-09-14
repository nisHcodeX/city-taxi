package com.citytaxi.city_taxi.models.dtos.account.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDeleteResponse {
    private Long id;
    private String username;
    private String password;
    private String status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
