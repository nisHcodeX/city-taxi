package com.citytaxi.city_taxi.repositories;

import com.citytaxi.city_taxi.models.dtos.admin.response.AdminDashboardResponse;

public interface IDashboardRepository {
    AdminDashboardResponse getAdminDashboardData();
}
