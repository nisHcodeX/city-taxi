package com.citytaxi.city_taxi.services;

import com.citytaxi.city_taxi.models.dtos.booking.request.BookingCreateRequest;
import com.citytaxi.city_taxi.models.dtos.booking.request.BookingUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.booking.response.BookingCreateResponse;
import com.citytaxi.city_taxi.models.dtos.booking.response.BookingGetResponse;
import com.citytaxi.city_taxi.models.dtos.booking.response.BookingUpdateResponse;
import com.citytaxi.city_taxi.models.enums.EBookingStatus;

import java.util.List;

public interface IBookingService {
    List<BookingCreateResponse> create(List<BookingCreateRequest> payload);
    List<BookingUpdateResponse> update(List<BookingUpdateRequest> payload);
    List<BookingGetResponse> markAsCompleted(List<Long> ids);
    List<BookingGetResponse> getBookings(Long bookingId, Long driverId, Long customerId, EBookingStatus status);
}
