package com.citytaxi.city_taxi.services;

import com.citytaxi.city_taxi.models.dtos.driver.request.DriverCreateRequest;
import com.citytaxi.city_taxi.models.dtos.driver.request.DriverUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.driver.response.*;

import java.util.List;

public interface IDriverService {
    List<DriverCreateResponse> create(List<DriverCreateRequest> payload);
    List<DriverUpdateResponse> update(List<DriverUpdateRequest> payload);
    List<DriverDeleteResponse> delete(List<Long> ids);
    List<DriverGetResponse> getDrivers(Long id);
    DriverRegistrationResponse registerDriver(DriverCreateRequest payload);
    List<DriverGetNearbyResponse> getNearbyDrivers(Double customerLat, Double customerLng, Double radius);
}
