package com.citytaxi.city_taxi.repositories;

import com.citytaxi.city_taxi.models.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {}
