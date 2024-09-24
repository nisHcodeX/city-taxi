package com.citytaxi.city_taxi.repositories;

import com.citytaxi.city_taxi.models.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByLicensePlate(String licensePlate);
    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Vehicle v WHERE v.id != ?1 AND v.licensePlate ILIKE ?2")
    boolean existsByLicensePlate(Long id, String licensePlate);
}
