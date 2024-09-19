package com.citytaxi.city_taxi.controllers;

import com.citytaxi.city_taxi.models.dtos.customer.request.CustomerRegistrationRequest;
import com.citytaxi.city_taxi.models.dtos.customer.request.CustomerUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.customer.response.CustomerGetResponse;
import com.citytaxi.city_taxi.services.IAccountService;
import com.citytaxi.city_taxi.services.ICustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/customer")
public class CustomerController {
    private final ICustomerService customerService;

    /**
     * Retrieves customer data based on the provided ID.
     *
     * @param id The ID of the customer to be retrieved. If null, retrieves all customers.
     * @return A ResponseEntity containing a list of CustomerGetResponse objects.
     */
    @GetMapping
    public ResponseEntity<?> getCustomers(@RequestParam(value = "id", required = false) Long id) {
        final List<CustomerGetResponse> customers = customerService.getCustomers(id);

        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(customers);
    }

    /**
     * Registers a new customer based on the provided payload.
     *
     * @param payload A CustomerRegistrationRequest objects containing the details of the customer to be created.
     * @return A ResponseEntity containing the created customer and a CREATED status.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody CustomerRegistrationRequest payload) {
        return new ResponseEntity<>(customerService.registerCustomer(payload), HttpStatus.CREATED);
    }

    /**
     * Updates existing customers based on the provided payload.
     *
     * @param payload A list of CustomerUpdateRequest objects containing the updated details of the customers.
     * @return A ResponseEntity containing the updated customers and an OK status.
     */
    @PatchMapping
    public ResponseEntity<?> updateCustomer(@Valid @RequestBody List<CustomerUpdateRequest> payload) {
        return new ResponseEntity<>(customerService.update(payload), HttpStatus.OK);
    }

    /**
     * Deletes customers based on the provided IDs.
     *
     * @param ids A list of IDs of the customers to be deleted.
     * @return A ResponseEntity containing the result of the delete operation and an OK status.
     */
    @DeleteMapping
    public ResponseEntity<?> deleteCustomer(@RequestParam("ids") List<Long> ids) {
        return new ResponseEntity<>(customerService.delete(ids), HttpStatus.OK);
    }


}
