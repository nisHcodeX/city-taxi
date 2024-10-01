package com.citytaxi.city_taxi.services.impl;

import com.citytaxi.city_taxi.exceptions.BadRequestException;
import com.citytaxi.city_taxi.exceptions.NotFoundException;
import com.citytaxi.city_taxi.models.dtos.email.SendRegistrationEmailRequest;
import com.citytaxi.city_taxi.models.dtos.telephone_operator.request.TelephoneOperatorCreateRequest;
import com.citytaxi.city_taxi.models.dtos.telephone_operator.request.TelephoneOperatorUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.telephone_operator.response.TelephoneOperatorCreateResponse;
import com.citytaxi.city_taxi.models.dtos.telephone_operator.response.TelephoneOperatorDeleteResponse;
import com.citytaxi.city_taxi.models.dtos.telephone_operator.response.TelephoneOperatorGetResponse;
import com.citytaxi.city_taxi.models.dtos.telephone_operator.response.TelephoneOperatorUpdateResponse;
import com.citytaxi.city_taxi.models.entities.Account;
import com.citytaxi.city_taxi.models.entities.TelephoneOperator;
import com.citytaxi.city_taxi.models.enums.EAccountStatus;
import com.citytaxi.city_taxi.models.enums.EAccountType;
import com.citytaxi.city_taxi.repositories.AccountRepository;
import com.citytaxi.city_taxi.repositories.TelephoneOperatorRepository;
import com.citytaxi.city_taxi.services.ITelephoneOperatorService;
import com.citytaxi.city_taxi.util.PasswordGenerator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

@Service
@Log4j2
@AllArgsConstructor
public class TelephoneOperatorService implements ITelephoneOperatorService {
    private final AccountRepository accountRepository;
    private final TelephoneOperatorRepository telephoneOperatorRepository;
    private final BlockingQueue<SendRegistrationEmailRequest> emailQueue;
    private final PasswordGenerator passwordGenerator;

    /**
     * Creates a new telephone operator.
     *
     * @param payload The request payload containing the details of the telephone operator to be created.
     * @return The response containing the details of the created telephone operator.
     * @throws BadRequestException If a telephone operator with the given email or phone number already exists.
     */
    @Override
    public TelephoneOperatorCreateResponse create(TelephoneOperatorCreateRequest payload) {
        if (telephoneOperatorRepository.existsByEmailOrPhoneNumber(payload.getEmail(), payload.getPhoneNumber())) {
            throw new BadRequestException("Telephone operator with email or phone number already exists");
        }

        final String password = passwordGenerator.generatePassword(8);

        // Create the account instance
        Account account = Account.builder()
                .username(payload.getEmail())
                .password(password)
                .status(EAccountStatus.ACTIVE)
                .accountType(EAccountType.TELEPHONE_OPERATOR)
                .createdAt(OffsetDateTime.now())
                .build();
        accountRepository.save(account);

        // Create the telephone operator instance
        final TelephoneOperator telephoneOperator = TelephoneOperator.builder()
                .name(payload.getName())
                .email(payload.getEmail())
                .phoneNumber(payload.getPhoneNumber())
                .account(account)
                .createdAt(OffsetDateTime.now())
                .build();
        telephoneOperatorRepository.save(telephoneOperator);
        log.debug("telephone operator registered");

        // Send registration email
        SendRegistrationEmailRequest emailRequest = SendRegistrationEmailRequest.builder()
                .to(payload.getEmail())
                .subject("City Taxi Telephone Operator Registration")
                .username(payload.getEmail())
                .password(password)
                .build();
        emailQueue.add(emailRequest);

        return TelephoneOperatorCreateResponse.builder()
                .id(telephoneOperator.getId())
                .name(telephoneOperator.getName())
                .email(telephoneOperator.getEmail())
                .phoneNumber(telephoneOperator.getPhoneNumber())
                .createdAt(telephoneOperator.getCreatedAt())
                .build();
    }

    /**
     * Updates the details of existing telephone operators.
     *
     * @param payload The list of request payloads containing the details of the telephone operators to be updated.
     * @return The list of responses containing the updated details of the telephone operators.
     * @throws NotFoundException If a telephone operator with the given ID is not found.
     * @throws BadRequestException If a telephone operator with the given email or phone number already exists.
     */
    @Override
    @Transactional
    public List<TelephoneOperatorUpdateResponse> update(List<TelephoneOperatorUpdateRequest> payload) {
        final List<TelephoneOperatorUpdateResponse> response = new ArrayList<>();

        for (TelephoneOperatorUpdateRequest request : payload) {
            final TelephoneOperator telephoneOperator = telephoneOperatorRepository.findById(request.getId()).orElseThrow(
                    () -> new NotFoundException(String.format("Telephone operator with ID %d not found", request.getId()))
            );

            if (request.getName() != null) {
                telephoneOperator.setName(request.getName());
            }

            if (request.getEmail() != null) {
                if (telephoneOperatorRepository.existsByEmail(request.getId(), request.getEmail())) {
                    throw new BadRequestException("Telephone operator with email already exists");
                }
                telephoneOperator.setEmail(request.getEmail());
            }

            if (request.getPhoneNumber() != null) {
                if (telephoneOperatorRepository.existsByPhoneNumber(request.getId(), request.getPhoneNumber())) {
                    throw new BadRequestException("Telephone operator with phone number already exists");
                }
                telephoneOperator.setPhoneNumber(request.getPhoneNumber());
            }

            telephoneOperator.setUpdatedAt(OffsetDateTime.now());
            telephoneOperatorRepository.save(telephoneOperator);
            log.debug("telephone operator updated");

            response.add(TelephoneOperatorUpdateResponse.builder()
                    .id(telephoneOperator.getId())
                    .name(telephoneOperator.getName())
                    .email(telephoneOperator.getEmail())
                    .phoneNumber(telephoneOperator.getPhoneNumber())
                    .createdAt(telephoneOperator.getCreatedAt())
                    .updatedAt(telephoneOperator.getUpdatedAt())
                    .build());
        }
        return response;
    }

    /**
     * Deletes the specified telephone operators.
     *
     * @param ids The list of IDs of the telephone operators to be deleted.
     * @return The list of responses containing the details of the deleted telephone operators.
     * @throws NotFoundException If a telephone operator with the given ID is not found.
     */
    @Override
    public List<TelephoneOperatorDeleteResponse> delete(List<Long> ids) {
        final List<TelephoneOperatorDeleteResponse> response = new ArrayList<>();

        for (Long id : ids) {
            final TelephoneOperator telephoneOperator = telephoneOperatorRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Telephone operator with ID %d not found", id))
            );

            telephoneOperatorRepository.delete(telephoneOperator);
            log.debug("telephone operator deleted");

            response.add(TelephoneOperatorDeleteResponse.builder()
                    .id(telephoneOperator.getId())
                    .name(telephoneOperator.getName())
                    .email(telephoneOperator.getEmail())
                    .phoneNumber(telephoneOperator.getPhoneNumber())
                    .createdAt(telephoneOperator.getCreatedAt())
                    .updatedAt(telephoneOperator.getUpdatedAt())
                    .build());
        }

        return response;
    }

    /**
     * Retrieves the details of telephone operators.
     *
     * @param id The ID of the telephone operator to be retrieved. If null, retrieves all telephone operators.
     * @return The list of responses containing the details of the retrieved telephone operators.
     * @throws NotFoundException If a telephone operator with the given ID is not found.
     */
    @Override
    public List<TelephoneOperatorGetResponse> getTelephoneOperators(Long id) {
        final List<TelephoneOperatorGetResponse> response = new ArrayList<>();

        if (id != null) {
            TelephoneOperator telephoneOperator = telephoneOperatorRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Telephone operator with ID %d not found", id))
            );

            response.add(TelephoneOperatorGetResponse.builder()
                    .id(telephoneOperator.getId())
                    .name(telephoneOperator.getName())
                    .email(telephoneOperator.getEmail())
                    .phoneNumber(telephoneOperator.getPhoneNumber())
                    .createdAt(telephoneOperator.getCreatedAt())
                    .updatedAt(telephoneOperator.getUpdatedAt())
                    .build());

            return response;
        }
        return telephoneOperatorRepository.findAllTelephoneOperators();
    }
}
