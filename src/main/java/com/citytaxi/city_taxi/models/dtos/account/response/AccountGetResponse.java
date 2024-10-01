package com.citytaxi.city_taxi.models.dtos.account.response;

import com.citytaxi.city_taxi.models.dtos.admin.response.AdminGetResponse;
import com.citytaxi.city_taxi.models.dtos.customer.response.CustomerGetResponse;
import com.citytaxi.city_taxi.models.dtos.driver.response.DriverGetResponse;
import com.citytaxi.city_taxi.models.dtos.telephone_operator.response.TelephoneOperatorGetResponse;
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
    private CustomerGetResponse customer;
    private DriverGetResponse driver;
    private TelephoneOperatorGetResponse telephoneOperator;
    private AdminGetResponse admin;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
