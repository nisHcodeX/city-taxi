package com.citytaxi.city_taxi.controllers;

import com.citytaxi.city_taxi.models.dtos.rating.request.RatingCreateRequest;
import com.citytaxi.city_taxi.models.dtos.rating.request.RatingUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.rating.response.RatingGetResponse;
import com.citytaxi.city_taxi.services.IRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/rating")
public class RatingController {
    private final IRatingService ratingService;

    @GetMapping
    public ResponseEntity<?> getRatings(@RequestParam(value = "customerId", required = false) Long customerId,
                                          @RequestParam(value = "driverId", required = false) Long driverId,
                                          @RequestParam(value = "bookingId", required = false) Long bookingId) {
        final List<RatingGetResponse> ratings = ratingService.getRatings(customerId, driverId, bookingId);

        if (ratings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(ratings);
    }

    @PostMapping
    public ResponseEntity<?> createRating(@Valid @RequestBody List<RatingCreateRequest> payload) {
        return new ResponseEntity<>(ratingService.create(payload), HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<?> updateRating(@Valid @RequestBody List<RatingUpdateRequest> payload) {
        return new ResponseEntity<>(ratingService.update(payload), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteRating(@RequestParam("ids") List<Long> ids) {
        return new ResponseEntity<>(ratingService.delete(ids), HttpStatus.OK);
    }
}
