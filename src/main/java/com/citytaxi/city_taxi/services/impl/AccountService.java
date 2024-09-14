package com.citytaxi.city_taxi.services.impl;

import com.citytaxi.city_taxi.exceptions.BadRequestException;
import com.citytaxi.city_taxi.exceptions.NotFoundException;
import com.citytaxi.city_taxi.models.dtos.account.request.AccountCreateRequest;
import com.citytaxi.city_taxi.models.dtos.account.request.AccountUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.account.response.AccountCreateResponse;
import com.citytaxi.city_taxi.models.dtos.account.response.AccountDeleteResponse;
import com.citytaxi.city_taxi.models.dtos.account.response.AccountGetResponse;
import com.citytaxi.city_taxi.models.dtos.account.response.AccountUpdateResponse;
import com.citytaxi.city_taxi.models.entities.Account;
import com.citytaxi.city_taxi.models.entities.Customer;
import com.citytaxi.city_taxi.models.enums.EAccountStatus;
import com.citytaxi.city_taxi.repositories.AccountRepository;
import com.citytaxi.city_taxi.repositories.CustomerRepository;
import com.citytaxi.city_taxi.services.IAccountService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class AccountService implements IAccountService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    /**
     * Creates new accounts based on the provided payload.
     *
     * @param payload A list of AccountCreateRequest objects containing the details of the accounts to be created.
     * @return A list of AccountCreateResponse objects containing the created accounts.
     * @throws BadRequestException if the username already exists.
     */
    @Override
    public List<AccountCreateResponse> create(List<AccountCreateRequest> payload) throws BadRequestException {
        final List<AccountCreateResponse> response = new ArrayList<>();

        for (AccountCreateRequest request : payload) {
            if (accountRepository.existsByUsername(request.getUsername())) {
                throw new BadRequestException("Username already exists");
            }

            Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
                    () -> new NotFoundException(String.format("Customer with ID %d not found", request.getCustomerId()))
            );

            Account account = Account.builder()
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .status(EAccountStatus.ACTIVE)
                    .createdAt(OffsetDateTime.now())
                    .build();

            accountRepository.save(account);
            log.debug("Account created");

            customer.setAccount(account);
            customerRepository.save(customer);

            response.add(AccountCreateResponse.builder()
                    .id(account.getId())
                    .username(request.getUsername())
                    .password(request.getPassword())
                    .createdAt(account.getCreatedAt())
                    .build());
        }

        return response;
    }

    /**
     * Updates existing accounts based on the provided payload.
     *
     * @param payload A list of AccountUpdateRequest objects containing the updated details of the accounts.
     * @return A list of AccountUpdateResponse objects containing the updated accounts.
     * @throws NotFoundException if the account with the specified ID is not found.
     * @throws BadRequestException if the username already exists.
     */
    @Override
    public List<AccountUpdateResponse> update(List<AccountUpdateRequest> payload) throws NotFoundException, BadRequestException {
        final List<AccountUpdateResponse> response = new ArrayList<>();

        for (AccountUpdateRequest request : payload) {
            Account account = accountRepository.findById(request.getId()).orElseThrow(
                    () -> new NotFoundException(String.format("Account with id %d not found", request.getId()))
            );

            if (request.getUsername() != null) {
                if (accountRepository.existsByUsername(request.getId(), request.getUsername())) {
                    throw new BadRequestException("Username already exists");
                }

                account.setUsername(request.getUsername());
            }

            if (request.getPassword() != null) {
                account.setPassword(request.getPassword());
            }

            if (request.getStatus() != null) {
                account.setStatus(request.getStatus());
            }

            account.setUpdatedAt(OffsetDateTime.now());
            accountRepository.save(account);
            log.debug("Account updated");

            response.add(AccountUpdateResponse.builder()
                    .id(account.getId())
                    .username(account.getUsername())
                    .password(account.getPassword())
                    .status(account.getStatus().name())
                    .updatedAt(account.getUpdatedAt())
                    .build());
        }

        return response;
    }

    /**
     * Deletes accounts based on the provided IDs.
     *
     * @param ids A list of IDs of the accounts to be deleted.
     * @return A list of AccountDeleteResponse objects containing the details of the deleted accounts.
     * @throws NotFoundException if the account with the specified ID is not found.
     */
    @Override
    public List<AccountDeleteResponse> delete(List<Long> ids) throws NotFoundException {
        final List<AccountDeleteResponse> response = new ArrayList<>();

        for (Long id : ids) {
            final Account account = accountRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Account with ID %d not found", id))
            );

            accountRepository.delete(account);

            response.add(AccountDeleteResponse.builder()
                    .id(account.getId())
                    .username(account.getUsername())
                    .status(account.getStatus().name())
                    .createdAt(account.getCreatedAt())
                    .updatedAt(account.getUpdatedAt())
                    .build());
        }

        return response;
    }

    /**
     * Retrieves accounts based on the provided ID.
     *
     * @param id The ID of the account to be retrieved. If null, retrieves all accounts.
     * @return A list of AccountGetResponse objects containing the details of the retrieved accounts.
     * @throws NotFoundException if the account with the specified ID is not found.
     */
    @Override
    public List<AccountGetResponse> getAccounts(Long id) throws NotFoundException {
        final List<AccountGetResponse> response = new ArrayList<>();

        if (id != null) {
            Account account = accountRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Account with ID %d not found", id))
            );

            return List.of(AccountGetResponse.builder()
                    .id(account.getId())
                    .username(account.getUsername())
                    .password(account.getPassword())
                    .status(account.getStatus())
                    .createdAt(account.getCreatedAt())
                    .updatedAt(account.getUpdatedAt())
                    .build());
        }

        return response;
    }
}
