package com.citytaxi.city_taxi.models.dtos.account.response;

import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@Getter
public class AccountUpdateResponse {
    private Long id;
    private String username;
    private String password;
    private String status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
