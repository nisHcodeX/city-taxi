package com.citytaxi.city_taxi.services;

import com.citytaxi.city_taxi.models.dtos.customer.request.CustomerCreateRequest;
import com.citytaxi.city_taxi.models.dtos.customer.request.CustomerRegistrationRequest;
import com.citytaxi.city_taxi.models.dtos.customer.request.CustomerUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.customer.response.*;

import java.util.List;

public interface ICustomerService {
    List<CustomerCreateResponse> create(List<CustomerCreateRequest> payload);
    List<CustomerUpdateResponse> update(List<CustomerUpdateRequest> payload);
    List<CustomerDeleteResponse> delete(List<Long> ids);
    List<CustomerGetResponse> getCustomers(Long id);
    CustomerRegistrationResponse registerCustomer(CustomerRegistrationRequest payload);
}
