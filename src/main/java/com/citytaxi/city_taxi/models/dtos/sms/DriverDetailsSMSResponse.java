package com.citytaxi.city_taxi.models.dtos.sms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DriverDetailsSMSResponse {
    private String name;
    private String phoneNumber;
}
