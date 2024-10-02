package com.citytaxi.city_taxi.repositories;

import com.citytaxi.city_taxi.models.dtos.rating.response.RatingGetResponse;
import com.citytaxi.city_taxi.models.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT new com.citytaxi.city_taxi.models.dtos.rating.response.RatingGetResponse(r.id, r.rating, r.createdAt, r.updatedAt) FROM Rating r WHERE r.customer.id = :customerId")
    List<RatingGetResponse> findAllByCustomerId(Long customerId);
    @Query("SELECT new com.citytaxi.city_taxi.models.dtos.rating.response.RatingGetResponse(r.id, r.rating, r.createdAt, r.updatedAt) FROM Rating r WHERE r.booking.id = :bookingId")
    List<RatingGetResponse> findAllByBookingId(Long bookingId);

    @Query("SELECT new com.citytaxi.city_taxi.models.dtos.rating.response.RatingGetResponse(r.id, r.rating, r.createdAt, r.updatedAt) " +
            "FROM Rating r " +
            "INNER JOIN Booking b ON r.booking.id = b.id " +
            "WHERE b.driver.id = :driverId")
    List<RatingGetResponse> findAllByDriverId(Long driverId);

    @Query("SELECT new com.citytaxi.city_taxi.models.dtos.rating.response.RatingGetResponse(r.id, r.rating, r.createdAt, r.updatedAt) FROM Rating r")
    List<RatingGetResponse> finalAllRatings();
}
