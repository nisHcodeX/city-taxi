package com.citytaxi.city_taxi.services;

import com.citytaxi.city_taxi.models.dtos.vehicle.request.VehicleCreateRequest;
import com.citytaxi.city_taxi.models.dtos.vehicle.request.VehicleUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.vehicle.response.VehicleCreateResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle.response.VehicleDeleteResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle.response.VehicleGetResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle.response.VehicleUpdateResponse;

import java.util.List;

public interface IVehicleService {
    List<VehicleCreateResponse> create(List<VehicleCreateRequest> payload);
    List<VehicleUpdateResponse> update(List<VehicleUpdateRequest> payload);
    List<VehicleDeleteResponse> delete(List<Long> ids);
    List<VehicleGetResponse> getVehicles(Long id);
}
