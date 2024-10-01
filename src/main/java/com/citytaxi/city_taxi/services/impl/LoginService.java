package com.citytaxi.city_taxi.services.impl;

import com.citytaxi.city_taxi.exceptions.NotFoundException;
import com.citytaxi.city_taxi.exceptions.UnauthorizedException;
import com.citytaxi.city_taxi.models.dtos.login.request.LoginRequest;
import com.citytaxi.city_taxi.models.dtos.login.response.LoginResponse;
import com.citytaxi.city_taxi.models.entities.*;
import com.citytaxi.city_taxi.models.enums.EAccountType;
import com.citytaxi.city_taxi.repositories.AccountRepository;
import com.citytaxi.city_taxi.services.ILoginService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public LoginResponse publicUserLogin(LoginRequest payload) throws NotFoundException {
        final List<EAccountType> accountTypes = List.of(EAccountType.CUSTOMER, EAccountType.DRIVER);
        if (!accountRepository.existsByUsernameAndAccountTypes(payload.getUsername(), accountTypes)) {
            throw new NotFoundException(String.format("Account with email %s not found", payload.getUsername()));
        }

        final Account account = accountRepository.findAccountByUsernameAndPasswordAndAccountTypes(payload.getUsername(), payload.getPassword(), accountTypes).orElseThrow(
                () -> new UnauthorizedException(String.format("Invalid credentials for account with email %s", payload.getUsername()))
        );

        // Get the name, email and phoneNumber of driver or customer associated with the account
        final Customer customer = account.getCustomer();
        final Driver driver = account.getDriver();
        String name = null;
        String email = null;
        String phoneNumber = null;

        if (customer != null) {
            name = customer.getName();
            email = customer.getEmail();
            phoneNumber = customer.getPhoneNumber();
        } else if (driver != null) {
            name = driver.getName();
            email = driver.getEmail();
            phoneNumber = driver.getPhoneNumber();
        }

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

    /**
     * Handles the login process for internal users such as admins and telephone operators.
     *
     * @param payload the login request payload containing username and password
     * @return a LoginResponse object containing account details
     * @throws NotFoundException if the account with the given username is not found
     * @throws UnauthorizedException if the credentials are invalid
     */
    @Override
    public LoginResponse internalUserLogin(LoginRequest payload) {
        final List<EAccountType> accountTypes = List.of(EAccountType.ADMIN, EAccountType.TELEPHONE_OPERATOR);
        if (!accountRepository.existsByUsernameAndAccountTypes(payload.getUsername(), accountTypes)) {
            throw new NotFoundException(String.format("Account with email %s not found", payload.getUsername()));
        }

        final Account account = accountRepository.findAccountByUsernameAndPasswordAndAccountTypes(payload.getUsername(), payload.getPassword(), accountTypes).orElseThrow(
                () -> new UnauthorizedException(String.format("Invalid credentials for account with email %s", payload.getUsername()))
        );

        // Get the name, email and phoneNumber of admin or telephone operator associated with the account
        final Admin admin = account.getAdmin();
        final TelephoneOperator telephoneOperator = account.getTelephoneOperator();
        String name = null;
        String email = null;
        String phoneNumber = null;

        if (admin != null) {
            name = admin.getName();
            email = admin.getEmail();
            phoneNumber = admin.getPhoneNumber();
        } else if (telephoneOperator != null) {
            name = telephoneOperator.getName();
            email = telephoneOperator.getEmail();
            phoneNumber = telephoneOperator.getPhoneNumber();
        }

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
