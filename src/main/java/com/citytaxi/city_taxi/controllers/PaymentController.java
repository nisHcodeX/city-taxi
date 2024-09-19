package com.citytaxi.city_taxi.controllers;

import com.citytaxi.city_taxi.models.dtos.payment.request.PaymentCreateRequest;
import com.citytaxi.city_taxi.models.dtos.payment.response.PaymentGetResponse;
import com.citytaxi.city_taxi.services.IPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/payments")
public class PaymentController {
    private final IPaymentService paymentService;

    /**
     * Retrieves payments based on the provided ID.
     *
     * @param id The ID of the payment to be retrieved. If null, retrieves all payments.
     * @return ResponseEntity containing a list of PaymentGetResponse objects or NO_CONTENT status if no payments are found.
     */
    @GetMapping
    public ResponseEntity<?> getPayments(@RequestParam(value = "id", required = false) Long id) {
        List<PaymentGetResponse> payments = paymentService.getPayments(id);
        if (payments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(paymentService.getPayments(id));
    }

    /**
     * Creates new payments based on the provided payload.
     *
     * @param payload A list of PaymentCreateRequest objects containing the details of the payments to be created.
     * @return ResponseEntity containing a list of PaymentCreateResponse objects with the created payments.
     */
    @PostMapping
    public ResponseEntity<?> updateAccount(@Valid @RequestBody List<PaymentCreateRequest> payload) {
        return new ResponseEntity<>(paymentService.create(payload), HttpStatus.OK);
    }
}
