package com.citytaxi.city_taxi.controllers;

import com.citytaxi.city_taxi.models.dtos.telephone_operator.request.TelephoneOperatorCreateRequest;
import com.citytaxi.city_taxi.models.dtos.telephone_operator.request.TelephoneOperatorUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.telephone_operator.response.TelephoneOperatorGetResponse;
import com.citytaxi.city_taxi.services.ITelephoneOperatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/telephone/operator")
public class TelephoneOperatorController {
    private final ITelephoneOperatorService telephoneOperatorService;

    /**
     * Retrieves telephone operators based on the provided ID.
     * If no ID is provided, retrieves all telephone operators.
     *
     * @param id The ID of the telephone operator to be retrieved (optional).
     * @return A ResponseEntity containing the list of telephone operators or a NO_CONTENT status if none are found.
     */
    @GetMapping
    public ResponseEntity<?> getTelephoneOperator(@RequestParam(value = "id", required = false) Long id) {
        final List<TelephoneOperatorGetResponse> telephoneOperators = telephoneOperatorService.getTelephoneOperators(id);

        if (telephoneOperators.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(telephoneOperators);
    }

    /**
     * Registers a new telephone operator.
     *
     * @param payload The request payload containing the details of the telephone operator to be created.
     * @return A ResponseEntity containing the created telephone operator and a CREATED status.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerTelephoneOperator(@Valid @RequestBody TelephoneOperatorCreateRequest payload) {
        return new ResponseEntity<>(telephoneOperatorService.create(payload), HttpStatus.CREATED);
    }

    /**
     * Updates the details of existing telephone operators.
     *
     * @param payload The list of request payloads containing the details of the telephone operators to be updated.
     * @return A ResponseEntity containing the updated telephone operators and an OK status.
     */
    @PatchMapping
    public ResponseEntity<?> updateTelephoneOperator(@Valid @RequestBody List<TelephoneOperatorUpdateRequest> payload) {
        return new ResponseEntity<>(telephoneOperatorService.update(payload), HttpStatus.OK);
    }

    /**
     * Deletes the specified telephone operators.
     *
     * @param ids The list of IDs of the telephone operators to be deleted.
     * @return A ResponseEntity containing the details of the deleted telephone operators and an OK status.
     */
    @DeleteMapping
    public ResponseEntity<?> deleteTelephoneOperator(@RequestParam("ids") List<Long> ids) {
        return new ResponseEntity<>(telephoneOperatorService.delete(ids), HttpStatus.OK);
    }
}
