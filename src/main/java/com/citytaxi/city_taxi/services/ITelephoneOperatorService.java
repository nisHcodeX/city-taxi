package com.citytaxi.city_taxi.services;

import com.citytaxi.city_taxi.models.dtos.telephone_operator.request.TelephoneOperatorCreateRequest;
import com.citytaxi.city_taxi.models.dtos.telephone_operator.request.TelephoneOperatorUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.telephone_operator.response.TelephoneOperatorCreateResponse;
import com.citytaxi.city_taxi.models.dtos.telephone_operator.response.TelephoneOperatorDeleteResponse;
import com.citytaxi.city_taxi.models.dtos.telephone_operator.response.TelephoneOperatorGetResponse;
import com.citytaxi.city_taxi.models.dtos.telephone_operator.response.TelephoneOperatorUpdateResponse;

import java.util.List;

public interface ITelephoneOperatorService {
    TelephoneOperatorCreateResponse create(TelephoneOperatorCreateRequest payload);
    List<TelephoneOperatorUpdateResponse> update(List<TelephoneOperatorUpdateRequest> payload);
    List<TelephoneOperatorDeleteResponse> delete(List<Long> ids);
    List<TelephoneOperatorGetResponse> getTelephoneOperators(Long id);
}
