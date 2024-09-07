package com.citytaxi.city_taxi.repositories;

import com.citytaxi.city_taxi.models.entities.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {}
