package com.citytaxi.city_taxi.services.impl;

import com.citytaxi.city_taxi.exceptions.BadRequestException;
import com.citytaxi.city_taxi.exceptions.NotFoundException;
import com.citytaxi.city_taxi.models.dtos.vehicle.request.VehicleCreateRequest;
import com.citytaxi.city_taxi.models.dtos.vehicle.request.VehicleUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.vehicle.response.VehicleCreateResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle.response.VehicleDeleteResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle.response.VehicleGetResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle.response.VehicleUpdateResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.response.VehicleTypeGetResponse;
import com.citytaxi.city_taxi.models.entities.Driver;
import com.citytaxi.city_taxi.models.entities.Vehicle;
import com.citytaxi.city_taxi.models.entities.VehicleType;
import com.citytaxi.city_taxi.repositories.DriverRepository;
import com.citytaxi.city_taxi.repositories.VehicleRepository;
import com.citytaxi.city_taxi.repositories.VehicleTypeRepository;
import com.citytaxi.city_taxi.services.IVehicleService;
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
public class VehicleService implements IVehicleService {
    private final VehicleTypeRepository vehicleTypeRepository;
    private final VehicleRepository vehicleRepository;
    private final DriverRepository driverRepository;

    /**
     * Creates a list of vehicles based on the provided payload.
     *
     * @param payload List of VehicleCreateRequest objects containing vehicle details.
     * @return List of VehicleCreateResponse objects containing created vehicle details.
     * @throws BadRequestException if a vehicle with the same manufacturer and model already exists or if the vehicle type is not found.
     */
    @Override
    @Transactional
    public List<VehicleCreateResponse> create(List<VehicleCreateRequest> payload) throws BadRequestException {
        final List<VehicleCreateResponse> response = new ArrayList<>();

        for (VehicleCreateRequest request : payload) {
            if (vehicleRepository.existsByLicensePlate(request.getLicensePlate())) {
                throw new BadRequestException(String.format("Vehicle with license plate %s already exists", request.getLicensePlate()));
            }

            final VehicleType vehicleType = vehicleTypeRepository.findById(request.getVehicleTypeId()).orElseThrow(
                    () -> new BadRequestException(String.format("Vehicle type with id %s not found", request.getVehicleTypeId())
            ));

            final Driver driver = driverRepository.findById(request.getDriverId()).orElseThrow(
                    () -> new BadRequestException(String.format("Driver with id %s not found", request.getDriverId())
            ));

            final VehicleTypeGetResponse vehicleTypeGetResponse = VehicleTypeGetResponse.builder()
                    .id(vehicleType.getId())
                    .name(vehicleType.getName())
                    .pricePerMeter(vehicleType.getPricePerMeter())
                    .seatCount(vehicleType.getSeatCount())
                    .createdAt(vehicleType.getCreatedAt())
                    .updatedAt(vehicleType.getUpdatedAt())
                    .build();

            Vehicle vehicle = Vehicle.builder()
                    .manufacturer(request.getManufacturer())
                    .model(request.getModel())
                    .colour(request.getColour())
                    .licensePlate(request.getLicensePlate())
                    .vehicleType(vehicleType)
                    .createdAt(OffsetDateTime.now())
                    .build();

            vehicleRepository.save(vehicle);
            log.debug("vehicle created");

            driver.setVehicles(new ArrayList<>(List.of(vehicle)));
            driverRepository.save(driver);
            log.debug("Vehicle assigned to driver");

            response.add(VehicleCreateResponse.builder()
                    .id(vehicle.getId())
                    .manufacturer(vehicle.getManufacturer())
                    .model(vehicle.getModel())
                    .colour(vehicle.getColour())
                    .licensePlate(vehicle.getLicensePlate())
                    .vehicleType(vehicleTypeGetResponse)
                    .createdAt(vehicle.getCreatedAt())
                    .build());
        }

        return response;
    }

    /**
     * Updates a list of vehicles based on the provided payload.
     *
     * @param payload List of VehicleUpdateRequest objects containing updated vehicle details.
     * @return List of VehicleUpdateResponse objects containing updated vehicle details.
     * @throws NotFoundException if a vehicle with the specified ID is not found or if the vehicle type is not found.
     * @throws BadRequestException if a vehicle with the same manufacturer and model or license plate already exists.
     */
    @Override
    @Transactional
    public List<VehicleUpdateResponse> update(List<VehicleUpdateRequest> payload) throws NotFoundException, BadRequestException {
        final List<VehicleUpdateResponse> response = new ArrayList<>();

        for (VehicleUpdateRequest request : payload) {
            final Vehicle vehicle = vehicleRepository.findById(request.getId()).orElseThrow(
                    () -> new NotFoundException(String.format("Vehicle with ID %d not found", request.getId()))
            );

            if (request.getManufacturer() != null) {
                vehicle.setManufacturer(request.getManufacturer());
            }

            // Check if there is a vehicle with the same manufacturer and model
            if (request.getModel() != null) {
                vehicle.setModel(request.getModel());
            }

            if (request.getColour() != null) {
                vehicle.setColour(request.getColour());
            }

            if (request.getLicensePlate() != null) {
                if (vehicleRepository.existsByLicensePlate(request.getId(), request.getLicensePlate())) {
                    throw new BadRequestException(String.format("Vehicle with license plate %s already exists", request.getLicensePlate()));
                }
                vehicle.setLicensePlate(request.getLicensePlate());
            }

            VehicleTypeGetResponse vehicleTypeGetResponse = null;
            if (request.getVehicleTypeId() != null) {
                VehicleType vehicleType = vehicleTypeRepository.findById(request.getVehicleTypeId()).orElseThrow(
                        () -> new NotFoundException(String.format("Vehicle type with ID %d not found", request.getVehicleTypeId()))
                );
                vehicle.setVehicleType(vehicleType);

                vehicleTypeGetResponse = VehicleTypeGetResponse.builder()
                        .id(vehicleType.getId())
                        .name(vehicleType.getName())
                        .pricePerMeter(vehicleType.getPricePerMeter())
                        .seatCount(vehicleType.getSeatCount())
                        .createdAt(vehicleType.getCreatedAt())
                        .updatedAt(vehicleType.getUpdatedAt())
                        .build();
            }

            vehicle.setUpdatedAt(OffsetDateTime.now());
            vehicleRepository.save(vehicle);
            log.debug("vehicle updated");

            response.add(VehicleUpdateResponse.builder()
                    .id(vehicle.getId())
                    .manufacturer(vehicle.getManufacturer())
                    .model(vehicle.getModel())
                    .colour(vehicle.getColour())
                    .licensePlate(vehicle.getLicensePlate())
                    .vehicleType(vehicleTypeGetResponse)
                    .createdAt(vehicle.getCreatedAt())
                    .updatedAt(vehicle.getUpdatedAt())
                    .build());
        }
        return response;
    }

    /**
     * Deletes a list of vehicles based on the provided IDs.
     *
     * @param ids List of vehicle IDs to be deleted.
     * @return List of VehicleDeleteResponse objects containing deleted vehicle details.
     * @throws NotFoundException if a vehicle with the specified ID is not found.
     */
    @Override
    @Transactional
    public List<VehicleDeleteResponse> delete(List<Long> ids) throws NotFoundException {
        final List<VehicleDeleteResponse> response = new ArrayList<>();

        for (Long id : ids) {
            final Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Vehicle with ID %d not found", id))
            );

            vehicleRepository.delete(vehicle);
            log.debug("vehicle deleted");

            response.add(VehicleDeleteResponse.builder()
                    .id(vehicle.getId())
                    .manufacturer(vehicle.getManufacturer())
                    .model(vehicle.getModel())
                    .colour(vehicle.getColour())
                    .licensePlate(vehicle.getLicensePlate())
                    .createdAt(vehicle.getCreatedAt())
                    .updatedAt(vehicle.getUpdatedAt())
                    .build());
        }
        return response;
    }

    /**
     * Retrieves a list of vehicles. If an ID is provided, retrieves the vehicle with the specified ID.
     *
     * @param id Optional vehicle ID to retrieve a specific vehicle.
     * @return List of VehicleGetResponse objects containing vehicle details.
     * @throws NotFoundException if a vehicle with the specified ID is not found.
     */
    @Override
    public List<VehicleGetResponse> getVehicles(Long id) throws NotFoundException {
        final List<VehicleGetResponse> responses = new ArrayList<>();

        if (id != null) {
            Vehicle vehicle = vehicleRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Vehicle with ID %d not found", id))
            );

            return List.of(VehicleGetResponse.builder()
                    .id(vehicle.getId())
                    .manufacturer(vehicle.getManufacturer())
                    .model(vehicle.getModel())
                    .colour(vehicle.getColour())
                    .licensePlate(vehicle.getLicensePlate())
                    .vehicleType(VehicleTypeGetResponse.builder()
                            .id(vehicle.getVehicleType().getId())
                            .name(vehicle.getVehicleType().getName())
                            .pricePerMeter(vehicle.getVehicleType().getPricePerMeter())
                            .seatCount(vehicle.getVehicleType().getSeatCount())
                            .createdAt(vehicle.getVehicleType().getCreatedAt())
                            .updatedAt(vehicle.getVehicleType().getUpdatedAt())
                            .build())
                    .createdAt(vehicle.getCreatedAt())
                    .updatedAt(vehicle.getUpdatedAt())
                    .build());
        }

        final List<Vehicle> vehicles = vehicleRepository.findAll();
        for (Vehicle vehicle : vehicles) {
            responses.add(VehicleGetResponse.builder()
                    .id(vehicle.getId())
                    .manufacturer(vehicle.getManufacturer())
                    .model(vehicle.getModel())
                    .colour(vehicle.getColour())
                    .licensePlate(vehicle.getLicensePlate())
                    .vehicleType(VehicleTypeGetResponse.builder()
                            .id(vehicle.getVehicleType().getId())
                            .name(vehicle.getVehicleType().getName())
                            .pricePerMeter(vehicle.getVehicleType().getPricePerMeter())
                            .seatCount(vehicle.getVehicleType().getSeatCount())
                            .createdAt(vehicle.getVehicleType().getCreatedAt())
                            .updatedAt(vehicle.getVehicleType().getUpdatedAt())
                            .build())
                    .createdAt(vehicle.getCreatedAt())
                    .updatedAt(vehicle.getUpdatedAt())
                    .build());
        }
        return responses;
    }
}
