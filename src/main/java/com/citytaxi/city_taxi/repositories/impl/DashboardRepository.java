package com.citytaxi.city_taxi.repositories.impl;

import com.citytaxi.city_taxi.models.dtos.admin.response.AdminDashboardResponse;
import com.citytaxi.city_taxi.repositories.IDashboardRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DashboardRepository implements IDashboardRepository {
    private final EntityManager entityManager;

    /**
     * Retrieves the dashboard data including the total number of customers, drivers, and bookings.
     *
     * @return AdminDashboardResponse containing the total counts of customers, drivers, and bookings.
     */
    @Override
    public AdminDashboardResponse getAdminDashboardData() {
        String sql = "SELECT " +
                "(SELECT COUNT(*) FROM customer) AS totalCustomers, " +
                "(SELECT COUNT(*) FROM driver) AS totalDrivers, " +
                "(SELECT COUNT(*) FROM booking) AS totalBookings";

        Query query = entityManager.createNativeQuery(sql);
        Object[] result = (Object[]) query.getSingleResult();

        return AdminDashboardResponse.builder()
                .totalCustomers(((Number) result[0]).intValue())
                .totalDrivers(((Number) result[1]).intValue())
                .totalBookings(((Number) result[2]).intValue())
                .build();
    }
}
