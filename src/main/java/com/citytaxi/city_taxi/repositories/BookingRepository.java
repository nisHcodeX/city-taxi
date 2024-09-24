package com.citytaxi.city_taxi.repositories;

import com.citytaxi.city_taxi.models.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {}
