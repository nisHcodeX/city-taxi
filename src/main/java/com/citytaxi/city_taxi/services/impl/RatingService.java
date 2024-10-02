package com.citytaxi.city_taxi.services.impl;

import com.citytaxi.city_taxi.exceptions.NotFoundException;
import com.citytaxi.city_taxi.models.dtos.rating.request.RatingCreateRequest;
import com.citytaxi.city_taxi.models.dtos.rating.request.RatingUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.rating.response.RatingCreateResponse;
import com.citytaxi.city_taxi.models.dtos.rating.response.RatingDeleteResponse;
import com.citytaxi.city_taxi.models.dtos.rating.response.RatingGetResponse;
import com.citytaxi.city_taxi.models.dtos.rating.response.RatingUpdateResponse;
import com.citytaxi.city_taxi.models.entities.Booking;
import com.citytaxi.city_taxi.models.entities.Rating;
import com.citytaxi.city_taxi.repositories.BookingRepository;
import com.citytaxi.city_taxi.repositories.CustomerRepository;
import com.citytaxi.city_taxi.repositories.RatingRepository;
import com.citytaxi.city_taxi.services.IRatingService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class RatingService implements IRatingService {
    private final RatingRepository ratingRepository;
    private final CustomerRepository customerRepository;
    private final BookingRepository bookingRepository;

    /**
     * Creates new ratings based on the provided payload.
     *
     * @param payload A list of RatingCreateRequest objects containing the details for the new ratings.
     * @return A list of RatingCreateResponse objects containing the details of the created ratings.
     * @throws NotFoundException if the customer or booking associated with a request is not found.
     */
    @Override
    @Transactional
    public List<RatingCreateResponse> create(List<RatingCreateRequest> payload) {
        final List<RatingCreateResponse> response = new ArrayList<>();

        for (RatingCreateRequest request : payload) {
            final Booking booking = bookingRepository.findById(request.getBookingId()).orElseThrow(
                    () -> new NotFoundException(String.format("Booking with id %d not found", request.getBookingId()))
            );

            final Rating rating = Rating.builder()
                    .rating(request.getRating())
                    .customer(booking.getCustomer())
                    .booking(booking)
                    .createdAt(OffsetDateTime.now())
                    .build();

            ratingRepository.save(rating);
            log.debug("rating created");

            response.add(RatingCreateResponse.builder()
                    .id(rating.getId())
                    .rating(rating.getRating())
                    .createdAt(rating.getCreatedAt())
                    .build());
        }

        return response;
    }

    /**
     * Updates existing ratings based on the provided payload.
     *
     * @param payload A list of RatingUpdateRequest objects containing the details for the ratings to be updated.
     * @return A list of RatingUpdateResponse objects containing the details of the updated ratings.
     * @throws NotFoundException if a rating with the specified ID is not found.
     */
    @Override
    @Transactional
    public List<RatingUpdateResponse> update(List<RatingUpdateRequest> payload) {
        final List<RatingUpdateResponse> response = new ArrayList<>();

        for (RatingUpdateRequest request : payload) {
            final Rating rating = ratingRepository.findById(request.getId()).orElseThrow(
                    () -> new NotFoundException(String.format("Rating with ID %d not found", request.getId()))
            );

            if (request.getRating() != null) {
                rating.setRating(request.getRating());
            }
            rating.setUpdatedAt(OffsetDateTime.now());
            ratingRepository.save(rating);
            log.debug("rating updated");

            response.add(RatingUpdateResponse.builder()
                    .id(rating.getId())
                    .rating(rating.getRating())
                    .createdAt(rating.getCreatedAt())
                    .updatedAt(rating.getUpdatedAt())
                    .build());
        }
        return response;
    }

    /**
     * Deletes ratings based on the provided list of IDs.
     *
     * @param ids A list of IDs of the ratings to be deleted.
     * @return A list of RatingDeleteResponse objects containing the details of the deleted ratings.
     * @throws NotFoundException if a rating with the specified ID is not found.
     */
    @Override
    @Transactional
    public List<RatingDeleteResponse> delete(List<Long> ids) {
        final List<RatingDeleteResponse> response;
        response = new ArrayList<>();

        for (Long id : ids) {
            final Rating rating = ratingRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Rating with ID %d not found", id))
            );

            ratingRepository.delete(rating);

            response.add(RatingDeleteResponse.builder()
                    .id(rating.getId())
                    .rating(rating.getRating())
                    .createdAt(rating.getCreatedAt())
                    .updatedAt(rating.getUpdatedAt())
                    .build());
        }

        log.debug("rating(s) deleted");
        return response;
    }

    /**
     * Retrieves ratings based on the provided customer ID or booking ID.
     *
     * @param customerId The ID of the customer whose ratings are to be retrieved.
     * @param bookingId The ID of the booking whose ratings are to be retrieved.
     * @return A list of RatingGetResponse objects containing the details of the retrieved ratings.
     * @throws NotFoundException if the customer or booking with the specified ID is not found.
     */
    @Override
    public List<RatingGetResponse> getRatings(Long customerId, Long bookingId) {
        if (customerId != null) {
            customerRepository.findById(customerId).orElseThrow(
                    () -> new NotFoundException(String.format("Customer with ID %d not found", customerId))
            );
            return ratingRepository.findAllByCustomerId(customerId);
        }

        if (bookingId != null) {
            bookingRepository.findById(bookingId).orElseThrow(
                    () -> new NotFoundException(String.format("Booking with ID %d not found", bookingId))
            );
            return ratingRepository.findAllByBookingId(bookingId);
        }
        return ratingRepository.finalAllRatings();
    }
}
