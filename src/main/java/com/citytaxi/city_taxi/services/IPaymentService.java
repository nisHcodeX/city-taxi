package com.citytaxi.city_taxi.services;

import com.citytaxi.city_taxi.models.dtos.payment.request.PaymentCreateRequest;
import com.citytaxi.city_taxi.models.dtos.payment.response.PaymentCreateResponse;
import com.citytaxi.city_taxi.models.dtos.payment.response.PaymentGetResponse;

import java.util.List;

public interface IPaymentService {
    List<PaymentCreateResponse> create(List<PaymentCreateRequest> payload);
    List<PaymentGetResponse> getPayments(Long id);
}
