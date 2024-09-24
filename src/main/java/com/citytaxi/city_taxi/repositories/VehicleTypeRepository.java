package com.citytaxi.city_taxi.repositories;

import com.citytaxi.city_taxi.models.dtos.vehicle_type.response.VehicleTypeGetResponse;
import com.citytaxi.city_taxi.models.entities.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {
    boolean existsByName(String name);
    @Query("SELECT CASE WHEN COUNT(vt) > 0 THEN true ELSE false END FROM VehicleType vt WHERE vt.id != ?1 AND vt.name ILIKE ?2")
    boolean existsByName(Long id, String name);
    @Query("SELECT new com.citytaxi.city_taxi.models.dtos.vehicle_type.response.VehicleTypeGetResponse(vt.id, vt.name, vt.pricePerMeter, vt.seatCount, vt.createdAt, vt.updatedAt) FROM VehicleType vt")
    List<VehicleTypeGetResponse> findAllVehicleTypes();
}
