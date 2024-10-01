package com.citytaxi.city_taxi.repositories;

import com.citytaxi.city_taxi.models.entities.Account;
import com.citytaxi.city_taxi.models.enums.EAccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByUsername(String username);
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Account a WHERE a.username = ?1 AND a.accountType IN ?2")
    boolean existsByUsernameAndAccountTypes(String username, List<EAccountType> accountTypes);
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Account a WHERE a.id != ?1 AND a.username = ?2")
    boolean existsByUsername(Long id, String username);
    @Query("SELECT a FROM Account a WHERE a.username = ?1 AND a.password = ?2 AND a.accountType IN ?3")
    Optional<Account> findAccountByUsernameAndPasswordAndAccountTypes(String username, String password, List<EAccountType> accountTypes);
}
