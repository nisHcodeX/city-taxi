package com.citytaxi.city_taxi.controllers;

import com.citytaxi.city_taxi.models.dtos.account.request.AccountUpdateRequest;
import com.citytaxi.city_taxi.services.IAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/accounts")
public class AccountController {
    private final IAccountService accountService;

    /**
     * Retrieves account data based on the provided ID.
     *
     * @param id The ID of the account to be retrieved. If null, retrieves all accounts.
     * @return A ResponseEntity containing a list of AccountGetResponse objects.
     */
    @GetMapping
    public ResponseEntity<?> getAccounts(@RequestParam(value = "id", required = false) Long id) {
        return ResponseEntity.ok(accountService.getAccounts(id));
    }

    /**
     * Updates existing accounts based on the provided payload.
     *
     * @param payload A list of AccountUpdateRequest objects containing the updated details of the accounts.
     * @return A ResponseEntity containing the updated accounts and an OK status.
     */
    @PatchMapping
    public ResponseEntity<?> updateAccount(@Valid @RequestBody List<AccountUpdateRequest> payload) {
        return new ResponseEntity<>(accountService.update(payload), HttpStatus.OK);
    }

    /**
     * Deletes accounts based on the provided IDs.
     *
     * @param ids A list of IDs of the accounts to be deleted.
     * @return A ResponseEntity containing the result of the delete operation and an OK status.
     */
    @DeleteMapping
    public ResponseEntity<?> deleteAccount(@RequestParam("ids") List<Long> ids) {
        return new ResponseEntity<>(accountService.delete(ids), HttpStatus.OK);
    }
}
