package com.citytaxi.city_taxi.models.dtos.admin.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminDashboardResponse {
    private Integer totalCustomers;
    private Integer totalDrivers;
    private Integer totalBookings;
}
