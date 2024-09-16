package com.citytaxi.city_taxi.services.impl;

import com.citytaxi.city_taxi.exceptions.BadRequestException;
import com.citytaxi.city_taxi.exceptions.NotFoundException;
import com.citytaxi.city_taxi.models.dtos.customer.request.CustomerCreateRequest;
import com.citytaxi.city_taxi.models.dtos.customer.request.CustomerRegistrationRequest;
import com.citytaxi.city_taxi.models.dtos.customer.request.CustomerUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.customer.response.*;
import com.citytaxi.city_taxi.models.dtos.email.SendRegistrationEmailRequest;
import com.citytaxi.city_taxi.models.entities.Account;
import com.citytaxi.city_taxi.models.entities.Customer;
import com.citytaxi.city_taxi.models.enums.EAccountStatus;
import com.citytaxi.city_taxi.models.enums.EAccountType;
import com.citytaxi.city_taxi.repositories.AccountRepository;
import com.citytaxi.city_taxi.repositories.CustomerRepository;
import com.citytaxi.city_taxi.services.ICustomerService;
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
public class CustomerService implements ICustomerService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final BlockingQueue<SendRegistrationEmailRequest> emailQueue;
    private final PasswordGenerator passwordGenerator;

    /**
     * Registers a new customer based on the provided payload.
     *
     * @param payload The CustomerRegistrationRequest object containing the details of the customer and account to be registered.
     * @return A CustomerRegistrationResponse object containing the details of the registered customer.
     * @throws BadRequestException If a customer with the provided email, phone number, or username already exists.
     */
    @Override
    public CustomerRegistrationResponse registerCustomer(CustomerRegistrationRequest payload) throws BadRequestException {
        if (customerRepository.existsByEmailOrPhoneNumber(payload.getEmail(), payload.getPhoneNumber())) {
            throw new BadRequestException("Customer with email or phone number already exists");
        }

        final String password = passwordGenerator.generatePassword(8);

        // Create the account instance
        Account account = Account.builder()
                .username(payload.getEmail())
                .password(password)
                .status(EAccountStatus.ACTIVE)
                .accountType(EAccountType.CUSTOMER)
                .createdAt(OffsetDateTime.now())
                .build();
        accountRepository.save(account);

        // Create the customer instance
        Customer customer = Customer.builder()
                .name(payload.getName())
                .email(payload.getEmail())
                .phoneNumber(payload.getPhoneNumber())
                .account(account)
                .createdAt(OffsetDateTime.now())
                .build();
        customerRepository.save(customer);
        log.debug("customer registered");

        // Send registration email
        SendRegistrationEmailRequest emailRequest = SendRegistrationEmailRequest.builder()
                .to(payload.getEmail())
                .subject("City Taxi Registration")
                .username(payload.getEmail())
                .password(password)
                .build();
        emailQueue.add(emailRequest);

        return CustomerRegistrationResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .username(account.getUsername())
                .password(account.getPassword())
                .status(account.getStatus())
                .createdAt(customer.getCreatedAt())
                .build();
    }

    /**
     * Creates new customers based on the provided payload.
     *
     * @param payload A list of CustomerCreateRequest objects containing the details of the customers to be created.
     * @return A CustomerCreateResponse object containing the details of the created customers.
     * @throws BadRequestException If the provided payload is invalid or contains errors.
     */
    @Override
    @Transactional
    public List<CustomerCreateResponse> create(List<CustomerCreateRequest> payload) throws BadRequestException {
        final List<CustomerCreateResponse> response = new ArrayList<>();

        for (CustomerCreateRequest request : payload) {
            if (customerRepository.existsByEmailOrPhoneNumber(request.getEmail(), request.getPhoneNumber())) {
                throw new BadRequestException("Customer with email or phone number already exists");
            }

            Customer customer = Customer.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .createdAt(OffsetDateTime.now())
                    .build();

            customerRepository.save(customer);
            log.debug("customer created");

            response.add(CustomerCreateResponse.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .email(customer.getEmail())
                    .phoneNumber(customer.getPhoneNumber())
                    .createdAt(customer.getCreatedAt())
                    .build());
        }

        return response;
    }

    /**
     * Updates existing customers based on the provided payload.
     *
     * @param payload A list of CustomerUpdateRequest objects containing the details of the customers to be updated.
     * @return A CustomerUpdateResponse object containing the details of the updated customers.
     * @throws NotFoundException   If any required resource is not found during the update process.
     * @throws BadRequestException If the provided payload is invalid or contains errors.
     */
    @Override
    @Transactional
    public List<CustomerUpdateResponse> update(List<CustomerUpdateRequest> payload) throws NotFoundException, BadRequestException {
        final List<CustomerUpdateResponse> response = new ArrayList<>();

        for (CustomerUpdateRequest request : payload) {
            final Customer customer = customerRepository.findById(request.getId()).orElseThrow(
                    () -> new NotFoundException(String.format("Customer with ID %d not found", request.getId()))
            );

            if (request.getName() != null) {
                customer.setName(request.getName());
            }

            if (request.getEmail() != null) {
                if (customerRepository.existsByEmail(request.getId(), request.getEmail())) {
                    throw new BadRequestException("Customer with email already exists");
                }
                customer.setEmail(request.getEmail());
            }

            if (request.getPhoneNumber() != null) {
                if (customerRepository.existsByPhoneNumber(request.getId(), request.getPhoneNumber())) {
                    throw new BadRequestException("Customer with phone number already exists");
                }
                customer.setPhoneNumber(request.getPhoneNumber());
            }

            customer.setUpdatedAt(OffsetDateTime.now());
            customerRepository.save(customer);

            response.add(CustomerUpdateResponse.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .email(customer.getEmail())
                    .phoneNumber(customer.getPhoneNumber())
                    .createdAt(customer.getCreatedAt())
                    .updatedAt(customer.getUpdatedAt())
                    .build());
        }
        return response;
    }

    /**
     * Deletes customers based on the provided list of IDs.
     *
     * @param ids A list of Long values representing the IDs of the customers to be deleted.
     * @return A CustomerDeleteResponse object containing the details of the deleted customers.
     * @throws NotFoundException If any customer with the provided IDs is not found.
     */
    @Override
    @Transactional
    public List<CustomerDeleteResponse> delete(List<Long> ids) throws NotFoundException {
        final List<CustomerDeleteResponse> response = new ArrayList<>();

        for (Long id : ids) {
            final Customer customer = customerRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Customer with ID %d not found", id))
            );

            customerRepository.delete(customer);

            response.add(CustomerDeleteResponse.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .email(customer.getEmail())
                    .phoneNumber(customer.getPhoneNumber())
                    .createdAt(customer.getCreatedAt())
                    .updatedAt(customer.getUpdatedAt())
                    .build());
        }

        return response;
    }

    /**
     * Retrieves customer data based on the provided ID.
     *
     * @param id The ID of the customer to be retrieved. If null, retrieves all customers.
     * @return A list of CustomerGetResponse objects containing the details of the retrieved customers.
     * @throws NotFoundException If a customer with the provided ID is not found.
     */
    @Override
    @Transactional
    public List<CustomerGetResponse> getCustomers(Long id) throws NotFoundException {
        final List<CustomerGetResponse> response = new ArrayList<>();

        if (id != null) {
            Customer customer = customerRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Customer with ID %d not found", id))
            );

            response.add(CustomerGetResponse.builder()
                    .id(customer.getId())
                    .name(customer.getName())
                    .email(customer.getEmail())
                    .phoneNumber(customer.getPhoneNumber())
                    .createdAt(customer.getCreatedAt())
                    .updatedAt(customer.getUpdatedAt())
                    .build());

            return response;
        }
        return customerRepository.findAllCustomers();
    }
}
