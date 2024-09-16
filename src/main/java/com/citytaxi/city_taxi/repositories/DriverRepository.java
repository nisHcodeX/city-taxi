package com.citytaxi.city_taxi.repositories;

import com.citytaxi.city_taxi.models.dtos.driver.response.DriverGetResponse;
import com.citytaxi.city_taxi.models.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Driver d WHERE d.id != ?1 AND d.email = ?2")
    boolean existsByEmail(Long id, String email);
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Driver d WHERE d.id != ?1 AND d.phoneNumber = ?2")
    boolean existsByPhoneNumber(Long id, String phoneNumber);
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Driver d WHERE d.id != ?1 AND d.driverLicense = ?2")
    boolean existsByDriverLicense(Long id, String driverLicense);
    @Query("SELECT new com.citytaxi.city_taxi.models.dtos.driver.response.DriverGetResponse(d.id, d.name, d.email, d.phoneNumber, d.driverLicense, d.createdAt, d.updatedAt) FROM Driver d")
    List<DriverGetResponse> findAllDrivers();
}
