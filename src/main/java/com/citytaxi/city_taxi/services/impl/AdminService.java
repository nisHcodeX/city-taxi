package com.citytaxi.city_taxi.services.impl;

import com.citytaxi.city_taxi.models.dtos.admin.response.AdminDashboardResponse;
import com.citytaxi.city_taxi.repositories.IDashboardRepository;
import com.citytaxi.city_taxi.services.IAdminService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class AdminService implements IAdminService {
    private final IDashboardRepository dashboardRepository;

    /**
     * Retrieves the dashboard data including the total number of customers, drivers, and bookings.
     *
     * @return AdminDashboardResponse containing the total counts of customers, drivers, and bookings.
     */
    @Override
    public AdminDashboardResponse getDashboardData() {
        return dashboardRepository.getAdminDashboardData();
    }
}
