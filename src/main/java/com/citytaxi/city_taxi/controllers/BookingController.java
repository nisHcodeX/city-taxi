package com.citytaxi.city_taxi.controllers;

import com.citytaxi.city_taxi.models.dtos.booking.request.BookingCreateRequest;
import com.citytaxi.city_taxi.models.dtos.booking.request.BookingUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.booking.response.BookingGetResponse;
import com.citytaxi.city_taxi.models.enums.EBookingStatus;
import com.citytaxi.city_taxi.services.IBookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/bookings")
public class BookingController {
    private final IBookingService bookingService;

    /**
     * Handles HTTP GET requests for retrieving bookings.
     *
     * @param id Optional query parameter for filtering bookings by their ID.
     * @param customerId Optional query parameter for filtering bookings by customer ID.
     * @param driverId Optional query parameter for filtering bookings by driver ID.
     * @return ResponseEntity containing a list of BookingGetResponse objects and HTTP status OK if bookings are found,
     *         or HTTP status NO_CONTENT if no bookings are found.
     */
    @GetMapping
    public ResponseEntity<?> getBookings(
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "customerId", required = false) Long customerId,
            @RequestParam(value = "driverId", required = false) Long driverId,
            @RequestParam(value = "status", required = false) EBookingStatus status) {
        final List<BookingGetResponse> bookings = bookingService.getBookings(id, driverId, customerId, status);
        if (bookings.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(bookings);
    }

    /**
     * Creates a list of bookings based on the provided payload.
     *
     * @param payload List of BookingCreateRequest objects containing booking details.
     * @return ResponseEntity containing the created bookings with HTTP status CREATED.
     */
    @PostMapping
    public ResponseEntity<?> createBooking(@Valid @RequestBody List<BookingCreateRequest> payload) {
        return new ResponseEntity<>(bookingService.create(payload), HttpStatus.CREATED);
    }

    /**
     * Updates a list of bookings based on the provided payload.
     *
     * @param payload List of BookingUpdateRequest objects containing updated booking details.
     * @return ResponseEntity containing the updated bookings with HTTP status OK.
     */
    @PatchMapping("/status")
    public ResponseEntity<?> updateBooking(@Valid @RequestBody List<BookingUpdateRequest> payload) {
        return new ResponseEntity<>(bookingService.update(payload), HttpStatus.OK);
    }

    /**
     * Marks the specified bookings as completed.
     *
     * @param ids List of booking IDs to be marked as completed.
     * @return ResponseEntity containing the result of the operation with HTTP status OK.
     */
    @PatchMapping("/mark/as/completed")
    public ResponseEntity<?> markAsCompleted(@RequestParam(value = "ids") List<Long> ids) {
        return new ResponseEntity<>(bookingService.markAsCompleted(ids), HttpStatus.OK);
    }

}
