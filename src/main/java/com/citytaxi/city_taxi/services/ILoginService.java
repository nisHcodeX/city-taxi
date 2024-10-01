package com.citytaxi.city_taxi.services;

import com.citytaxi.city_taxi.models.dtos.login.request.LoginRequest;
import com.citytaxi.city_taxi.models.dtos.login.response.LoginResponse;

public interface ILoginService {
    LoginResponse publicUserLogin(LoginRequest payload);
    LoginResponse internalUserLogin(LoginRequest payload);
}
