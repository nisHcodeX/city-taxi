package com.citytaxi.city_taxi.services;

import com.citytaxi.city_taxi.models.dtos.vehicle_type.request.VehicleTypeCreateRequest;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.request.VehicleTypeUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.response.VehicleTypeCreateResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.response.VehicleTypeDeleteResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.response.VehicleTypeGetResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.response.VehicleTypeUpdateResponse;

import java.util.List;

public interface IVehicleTypeService {
    List<VehicleTypeCreateResponse> create(List<VehicleTypeCreateRequest> payload);
    List<VehicleTypeUpdateResponse> update(List<VehicleTypeUpdateRequest> payload);
    List<VehicleTypeDeleteResponse> delete(List<Long> ids);
    List<VehicleTypeGetResponse> getVehicleTypes(Long id);
}
