package com.citytaxi.city_taxi.services;

import com.citytaxi.city_taxi.models.dtos.rating.request.RatingCreateRequest;
import com.citytaxi.city_taxi.models.dtos.rating.request.RatingUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.rating.response.RatingCreateResponse;
import com.citytaxi.city_taxi.models.dtos.rating.response.RatingDeleteResponse;
import com.citytaxi.city_taxi.models.dtos.rating.response.RatingGetResponse;
import com.citytaxi.city_taxi.models.dtos.rating.response.RatingUpdateResponse;

import java.util.List;

public interface IRatingService {
    List<RatingCreateResponse> create(List<RatingCreateRequest> payload);
    List<RatingUpdateResponse> update(List<RatingUpdateRequest> payload);
    List<RatingDeleteResponse> delete(List<Long> ids);
    List<RatingGetResponse> getRatings(Long customerId, Long bookingId);
}
