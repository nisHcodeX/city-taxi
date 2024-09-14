package com.citytaxi.city_taxi.services;

import com.citytaxi.city_taxi.models.dtos.customer.request.CustomerCreateRequest;
import com.citytaxi.city_taxi.models.dtos.customer.request.CustomerUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.customer.response.CustomerCreateResponse;
import com.citytaxi.city_taxi.models.dtos.customer.response.CustomerDeleteResponse;
import com.citytaxi.city_taxi.models.dtos.customer.response.CustomerGetResponse;
import com.citytaxi.city_taxi.models.dtos.customer.response.CustomerUpdateResponse;

import java.util.List;

public interface ICustomerService {
    List<CustomerCreateResponse> create(List<CustomerCreateRequest> payload);
    List<CustomerUpdateResponse> update(List<CustomerUpdateRequest> payload);
    List<CustomerDeleteResponse> delete(List<Long> ids);
    List<CustomerGetResponse> getCustomers(Long id);
}
