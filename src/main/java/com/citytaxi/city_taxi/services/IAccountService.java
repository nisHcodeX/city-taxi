package com.citytaxi.city_taxi.services;

import com.citytaxi.city_taxi.models.dtos.account.request.AccountCreateRequest;
import com.citytaxi.city_taxi.models.dtos.account.request.AccountUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.account.response.AccountCreateResponse;
import com.citytaxi.city_taxi.models.dtos.account.response.AccountDeleteResponse;
import com.citytaxi.city_taxi.models.dtos.account.response.AccountGetResponse;
import com.citytaxi.city_taxi.models.dtos.account.response.AccountUpdateResponse;

import java.util.List;

public interface IAccountService {
    List<AccountCreateResponse> create(List<AccountCreateRequest> payload);
    List<AccountUpdateResponse> update(List<AccountUpdateRequest> payload);
    List<AccountDeleteResponse> delete(List<Long> ids);
    List<AccountGetResponse> getAccounts(Long id);
}
