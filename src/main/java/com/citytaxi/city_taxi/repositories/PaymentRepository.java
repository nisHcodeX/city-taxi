package com.citytaxi.city_taxi.repositories;

import com.citytaxi.city_taxi.models.dtos.payment.response.PaymentGetResponse;
import com.citytaxi.city_taxi.models.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT new com.citytaxi.city_taxi.models.dtos.payment.response.PaymentGetResponse(p.id, p.cost, p.type, p.createdAt) FROM Payment p")
    List<PaymentGetResponse> findAllPayments();
}
