package com.citytaxi.city_taxi.services.impl;

import com.citytaxi.city_taxi.exceptions.BadRequestException;
import com.citytaxi.city_taxi.exceptions.NotFoundException;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.request.VehicleTypeCreateRequest;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.request.VehicleTypeUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.response.VehicleTypeCreateResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.response.VehicleTypeDeleteResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.response.VehicleTypeGetResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.response.VehicleTypeUpdateResponse;
import com.citytaxi.city_taxi.models.entities.VehicleType;
import com.citytaxi.city_taxi.repositories.VehicleTypeRepository;
import com.citytaxi.city_taxi.services.IVehicleTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
@AllArgsConstructor
public class VehicleTypeService implements IVehicleTypeService {
    private final VehicleTypeRepository vehicleTypeRepository;

    /**
     * Creates a list of vehicle types based on the provided payload.
     *
     * @param payload List of VehicleTypeCreateRequest objects containing vehicle type details.
     * @return List of VehicleTypeCreateResponse objects containing created vehicle type details.
     * @throws BadRequestException if a vehicle type with the same name already exists.
     */
    @Override
    @Transactional
    public List<VehicleTypeCreateResponse> create(List<VehicleTypeCreateRequest> payload) throws BadRequestException {
        final List<VehicleTypeCreateResponse> response = new ArrayList<>();

        for (VehicleTypeCreateRequest request : payload) {
            if (vehicleTypeRepository.existsByName(request.getName())) {
                throw new BadRequestException(String.format("Vehicle type with name %s already exists", request.getName()));
            }

            VehicleType vehicleType = VehicleType.builder()
                    .name(request.getName())
                    .pricePerMeter(request.getPricePerMeter())
                    .seatCount(request.getSeatCount())
                    .createdAt(OffsetDateTime.now())
                    .build();

            vehicleTypeRepository.save(vehicleType);
            log.debug("vehicle type created");

            response.add(VehicleTypeCreateResponse.builder()
                    .id(vehicleType.getId())
                    .name(vehicleType.getName())
                    .pricePerMeter(vehicleType.getPricePerMeter())
                    .seatCount(vehicleType.getSeatCount())
                    .createdAt(vehicleType.getCreatedAt())
                    .build());
        }

        return response;
    }

    /**
     * Updates a list of vehicle types based on the provided payload.
     *
     * @param payload List of VehicleTypeUpdateRequest objects containing updated vehicle type details.
     * @return List of VehicleTypeUpdateResponse objects containing updated vehicle type details.
     * @throws NotFoundException if a vehicle type with the specified ID is not found.
     * @throws BadRequestException if a vehicle type with the same name already exists.
     */
    @Override
    @Transactional
    public List<VehicleTypeUpdateResponse> update(List<VehicleTypeUpdateRequest> payload) throws NotFoundException, BadRequestException {
        final List<VehicleTypeUpdateResponse> response = new ArrayList<>();

        for (VehicleTypeUpdateRequest request : payload) {
            final VehicleType vehicleType = vehicleTypeRepository.findById(request.getId()).orElseThrow(
                    () -> new NotFoundException(String.format("Vehicle type with ID %d not found", request.getId()))
            );

            if (request.getName() != null) {
                if (vehicleTypeRepository.existsByName(request.getId(), request.getName())) {
                    throw new BadRequestException(String.format("Vehicle type with name %s already exists", request.getName()));
                }
                vehicleType.setName(request.getName());
            }

            if (request.getPricePerMeter() != null) {
                vehicleType.setPricePerMeter(request.getPricePerMeter());
            }

            if (request.getSeatCount() != null) {
                vehicleType.setSeatCount(request.getSeatCount());
            }

            vehicleType.setUpdatedAt(OffsetDateTime.now());
            vehicleTypeRepository.save(vehicleType);
            log.debug("vehicle type updated");

            response.add(VehicleTypeUpdateResponse.builder()
                    .id(vehicleType.getId())
                    .name(vehicleType.getName())
                    .pricePerMeter(vehicleType.getPricePerMeter())
                    .seatCount(vehicleType.getSeatCount())
                    .createdAt(vehicleType.getCreatedAt())
                    .updatedAt(vehicleType.getUpdatedAt())
                    .build());
        }
        return response;
    }

    /**
     * Deletes a list of vehicle types based on the provided IDs.
     *
     * @param ids List of vehicle type IDs to be deleted.
     * @return List of VehicleTypeDeleteResponse objects containing deleted vehicle type details.
     * @throws NotFoundException if a vehicle type with the specified ID is not found.
     */
    @Override
    @Transactional
    public List<VehicleTypeDeleteResponse> delete(List<Long> ids) {
        final List<VehicleTypeDeleteResponse> response = new ArrayList<>();

        for (Long id : ids) {
            final VehicleType vehicleType = vehicleTypeRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Vehicle type with ID %d not found", id))
            );

            vehicleTypeRepository.delete(vehicleType);
            log.debug("vehicle type deleted");

            response.add(VehicleTypeDeleteResponse.builder()
                    .id(vehicleType.getId())
                    .name(vehicleType.getName())
                    .pricePerMeter(vehicleType.getPricePerMeter())
                    .seatCount(vehicleType.getSeatCount())
                    .createdAt(vehicleType.getCreatedAt())
                    .updatedAt(vehicleType.getUpdatedAt())
                    .build());
        }

        return response;
    }

    /**
     * Retrieves a list of vehicle types. If an ID is provided, retrieves the vehicle type with the specified ID.
     *
     * @param id Optional vehicle type ID to retrieve a specific vehicle type.
     * @return List of VehicleTypeGetResponse objects containing vehicle type details.
     * @throws NotFoundException if a vehicle type with the specified ID is not found.
     */
    @Override
    public List<VehicleTypeGetResponse> getVehicleTypes(Long id) {
        if (id != null) {
            VehicleType vehicleType = vehicleTypeRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Vehicle type with ID %d not found", id))
            );

            return List.of(VehicleTypeGetResponse.builder()
                    .id(vehicleType.getId())
                    .name(vehicleType.getName())
                    .pricePerMeter(vehicleType.getPricePerMeter())
                    .seatCount(vehicleType.getSeatCount())
                    .createdAt(vehicleType.getCreatedAt())
                    .updatedAt(vehicleType.getUpdatedAt())
                    .build());
        }
        return vehicleTypeRepository.findAllVehicleTypes();
    }
}
