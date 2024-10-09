package com.citytaxi.city_taxi.repositories;

import com.citytaxi.city_taxi.models.entities.Booking;
import com.citytaxi.city_taxi.models.enums.EBookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerIdOrderByCreatedAt(Long customerId);
    List<Booking> findByCustomerIdAndStatusOrderByCreatedAtDesc(Long customerId, EBookingStatus status);
    List<Booking> findByDriverId(Long driverId);
    List<Booking> findByDriverIdAndStatusOrderByCreatedAtDesc(Long driverId, EBookingStatus status);
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b WHERE b.customer.id = ?1 AND b.status = 'COMPLETED'")
    boolean doesCustomerHaveUnpaidBooking(Long customerId);
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b WHERE b.customer.id = ?1 AND b.status = 'ACTIVE'")
    boolean doesCustomerHaveActiveBookings(Long customerId);

}
