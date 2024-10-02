package com.citytaxi.city_taxi.models.dtos.login.response;

import com.citytaxi.city_taxi.models.enums.EAccountStatus;
import com.citytaxi.city_taxi.models.enums.EAccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private Long accountId;
    private Long userId;
    private EAccountType accountType;
    private String username;
    private String password;
    private String name;
    private String email;
    private String phoneNumber;
    private EAccountStatus accountStatus;
}
