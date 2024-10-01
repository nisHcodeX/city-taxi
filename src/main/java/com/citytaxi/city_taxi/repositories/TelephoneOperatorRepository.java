package com.citytaxi.city_taxi.repositories;

import com.citytaxi.city_taxi.models.dtos.telephone_operator.response.TelephoneOperatorGetResponse;
import com.citytaxi.city_taxi.models.entities.TelephoneOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelephoneOperatorRepository extends JpaRepository<TelephoneOperator, Long> {
    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);
    @Query("SELECT CASE WHEN COUNT(to) > 0 THEN true ELSE false END FROM TelephoneOperator to WHERE to.id != ?1 AND to.email = ?2")
    boolean existsByEmail(Long id, String email);
    @Query("SELECT CASE WHEN COUNT(to) > 0 THEN true ELSE false END FROM TelephoneOperator to WHERE to.id != ?1 AND to.phoneNumber = ?2")
    boolean existsByPhoneNumber(Long id, String phoneNumber);
    @Query("SELECT new com.citytaxi.city_taxi.models.dtos.telephone_operator.response.TelephoneOperatorGetResponse(to.id, to.name, to.email, to.phoneNumber, to.createdAt, to.updatedAt) FROM TelephoneOperator to")
    List<TelephoneOperatorGetResponse> findAllTelephoneOperators();
}
