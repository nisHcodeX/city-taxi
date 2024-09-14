package com.citytaxi.city_taxi.repositories;

import com.citytaxi.city_taxi.models.dtos.account.response.AccountGetResponse;
import com.citytaxi.city_taxi.models.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByUsername(String username);
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Account a WHERE a.id != ?1 AND a.username = ?2")
    boolean existsByUsername(Long id, String username);

    @Query("SELECT new com.citytaxi.city_taxi.models.dtos.account.response.AccountGetResponse(a.id, a.username, a.password, a.status, a.createdAt, a.updatedAt) FROM Account a")
    List<AccountGetResponse> findAllAccount();
}
