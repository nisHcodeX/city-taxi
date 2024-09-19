package com.citytaxi.city_taxi.models.dtos.account.response;

import com.citytaxi.city_taxi.models.enums.EAccountStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@Getter
public class AccountCreateResponse {
    private Long id;
    private String username;
    private String password;
    private EAccountStatus status;
    private OffsetDateTime createdAt;
}
