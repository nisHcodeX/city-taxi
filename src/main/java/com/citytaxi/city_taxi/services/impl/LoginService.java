package com.citytaxi.city_taxi.services.impl;

import com.citytaxi.city_taxi.exceptions.NotFoundException;
import com.citytaxi.city_taxi.exceptions.UnauthorizedException;
import com.citytaxi.city_taxi.models.dtos.login.request.LoginRequest;
import com.citytaxi.city_taxi.models.dtos.login.response.LoginResponse;
import com.citytaxi.city_taxi.models.entities.Account;
import com.citytaxi.city_taxi.models.entities.Customer;
import com.citytaxi.city_taxi.repositories.AccountRepository;
import com.citytaxi.city_taxi.services.ILoginService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class LoginService implements ILoginService {
    private final AccountRepository accountRepository;

    /**
     * Handles the login process for both customers and drivers.
     *
     * @param payload the login request payload containing username and password
     * @return a LoginResponse object containing account details
     * @throws NotFoundException if the account with the given username is not found
     * @throws UnauthorizedException if the credentials are invalid
     */
    @Override
    public LoginResponse customerAndDriverLogin(LoginRequest payload) throws NotFoundException {
        if (!accountRepository.existsByUsername(payload.getUsername())) {
            throw new NotFoundException(String.format("Account with email %s not found", payload.getUsername()));
        }

        final Account account = accountRepository.findAccountByUsernameAndPassword(payload.getUsername(), payload.getPassword()).orElseThrow(
                () -> new UnauthorizedException(String.format("Invalid credentials for account with email %s", payload.getUsername()))
        );

        // Get the name, email and phoneNumber of driver or customer associated with the account
        final Customer customer = account.getCustomer();
        String name = customer != null ? customer.getName() : null;
        String email = customer != null ? customer.getEmail() : null;
        String phoneNumber = customer != null ? customer.getPhoneNumber() : null;

        // TODO: check if driver exists and do the same as above

        return LoginResponse.builder()
                .accountId(account.getId())
                .accountType(account.getAccountType())
                .username(account.getUsername())
                .password(account.getPassword())
                .name(name)
                .email(email)
                .phoneNumber(phoneNumber)
                .accountStatus(account.getStatus())
                .build();
    }
}
