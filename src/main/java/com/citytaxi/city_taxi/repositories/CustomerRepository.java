package com.citytaxi.city_taxi.repositories;

import com.citytaxi.city_taxi.models.dtos.customer.response.CustomerGetResponse;
import com.citytaxi.city_taxi.models.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Customer c WHERE c.id != ?1 AND c.email = ?2")
    boolean existsByEmail(Long id, String email);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Customer c WHERE c.id != ?1 AND c.phoneNumber = ?2")
    boolean existsByPhoneNumber(Long id, String phoneNumber);

    @Query("SELECT new com.citytaxi.city_taxi.models.dtos.customer.response.CustomerGetResponse(c.id, c.name, c.email, c.phoneNumber, c.createdAt, c.updatedAt) FROM Customer c")
    List<CustomerGetResponse> findAllCustomers();
}
