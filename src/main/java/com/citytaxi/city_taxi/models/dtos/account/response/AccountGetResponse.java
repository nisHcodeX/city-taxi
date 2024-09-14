package com.citytaxi.city_taxi.models.dtos.account.response;

import com.citytaxi.city_taxi.models.entities.Customer;
import com.citytaxi.city_taxi.models.enums.EAccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountGetResponse {
    private Long id;
    private String username;
    private String password;
    private EAccountStatus status;
    private Customer customer;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
