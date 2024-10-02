package com.citytaxi.city_taxi.services.impl;

import com.citytaxi.city_taxi.exceptions.NotFoundException;
import com.citytaxi.city_taxi.models.dtos.booking.request.BookingCreateRequest;
import com.citytaxi.city_taxi.models.dtos.booking.request.BookingUpdateRequest;
import com.citytaxi.city_taxi.models.dtos.booking.response.BookingCreateResponse;
import com.citytaxi.city_taxi.models.dtos.booking.response.BookingGetResponse;
import com.citytaxi.city_taxi.models.dtos.booking.response.BookingUpdateResponse;
import com.citytaxi.city_taxi.models.dtos.customer.response.CustomerGetResponse;
import com.citytaxi.city_taxi.models.dtos.driver.response.DriverGetResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle.response.VehicleGetResponse;
import com.citytaxi.city_taxi.models.dtos.vehicle_type.response.VehicleTypeGetResponse;
import com.citytaxi.city_taxi.models.entities.*;
import com.citytaxi.city_taxi.models.enums.EBookingStatus;
import com.citytaxi.city_taxi.models.enums.EDriverAvailabilityStatus;
import com.citytaxi.city_taxi.repositories.BookingRepository;
import com.citytaxi.city_taxi.repositories.CustomerRepository;
import com.citytaxi.city_taxi.repositories.DriverRepository;
import com.citytaxi.city_taxi.services.IBookingService;
import com.citytaxi.city_taxi.util.CostGenerator;
import com.citytaxi.city_taxi.util.DistanceCalculator;
import com.citytaxi.city_taxi.util.sms.SMSService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@AllArgsConstructor
public class BookingService implements IBookingService {
    private final BookingRepository bookingRepository;
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;
    private final CostGenerator costGenerator;
    private final DistanceCalculator distanceCalculator;
    private final SMSService smsService;

    /**
     * Creates a list of bookings based on the provided payload.
     *
     * @param payload List of BookingCreateRequest objects containing booking details.
     * @return List of BookingCreateResponse objects containing the created booking details.
     * @throws NotFoundException if the customer or driver is not found.
     */
    @Override
    @Transactional
    public List<BookingCreateResponse> create(List<BookingCreateRequest> payload) throws NotFoundException {
        final List<BookingCreateResponse> response = new ArrayList<>();

        for (BookingCreateRequest request : payload) {
            final Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow(
                    () -> new NotFoundException(String.format("Customer with id %s not found", request.getCustomerId())
            ));

            final Driver driver = driverRepository.findById(request.getDriverId()).orElseThrow(
                    () -> new NotFoundException(String.format("Driver with id %s not found", request.getDriverId())
            ));

            final Vehicle vehicle = driver.getVehicles().get(0);
            final VehicleType vehicleType = vehicle.getVehicleType();

            final double distance = distanceCalculator.calculateDistance(request.getStartLatitude(), request.getStartLongitude(), request.getDestLatitude(), request.getDestLongitude());
            log.debug("calculated distance = {} meters", distance);

            final double estimatedCost = costGenerator.generateCost(distance, vehicleType);
            log.debug("calculated cost = {}", estimatedCost);

            Booking booking = Booking.builder()
                    .estimatedCost(estimatedCost)
                    .startLatitude(request.getStartLatitude())
                    .startLongitude(request.getStartLongitude())
                    .destLatitude(request.getDestLatitude())
                    .destLongitude(request.getDestLongitude())
                    .distanceInMeters(distance)
                    .status(EBookingStatus.ACTIVE)
                    .customer(customer)
                    .driver(driver)
                    .build();

            bookingRepository.save(booking);
            log.debug("booking created");

            // Send SMS to customer phone number...
            final String bookingDetails = smsService.generateBookingDetailsForSMS(booking);
            smsService.sendSMS(customer.getPhoneNumber(), bookingDetails);

            // Update the availability of the driver to busy
            driver.setAvailability(EDriverAvailabilityStatus.BUSY);
            driverRepository.save(driver);

            response.add(BookingCreateResponse.builder()
                    .id(booking.getId())
                    .estimatedCost(booking.getEstimatedCost())
                    .distanceInMeters(distance)
                    .status(booking.getStatus())
                    .driver(DriverGetResponse.builder()
                            .id(driver.getId())
                            .email(driver.getEmail())
                            .phoneNumber(driver.getPhoneNumber())
                            .driverLicense(driver.getDriverLicense())
                            .createdAt(driver.getCreatedAt())
                            .updatedAt(driver.getUpdatedAt())
                            .build())
                    .customer(CustomerGetResponse.builder()
                            .id(customer.getId())
                            .name(customer.getName())
                            .email(customer.getEmail())
                            .phoneNumber(customer.getPhoneNumber())
                            .createdAt(customer.getCreatedAt())
                            .updatedAt(customer.getUpdatedAt())
                            .build())
                    .vehicle(VehicleGetResponse.builder()
                            .id(vehicle.getId())
                            .manufacturer(vehicle.getManufacturer())
                            .model(vehicle.getModel())
                            .colour(vehicle.getColour())
                            .licensePlate(vehicle.getLicensePlate())
                            .vehicleType(VehicleTypeGetResponse.builder()
                                    .id(vehicleType.getId())
                                    .name(vehicleType.getName())
                                    .pricePerMeter(vehicleType.getPricePerMeter())
                                    .seatCount(vehicleType.getSeatCount())
                                    .createdAt(vehicleType.getCreatedAt())
                                    .updatedAt(vehicleType.getUpdatedAt())
                                    .build())
                            .build())
                    .createdAt(booking.getCreatedAt())
                    .build());
        }

        return response;
    }

    /**
     * Updates a list of bookings based on the provided payload.
     *
     * @param payload List of BookingUpdateRequest objects containing updated booking details.
     * @return List of BookingUpdateResponse objects containing the updated booking details.
     * @throws NotFoundException if the booking is not found.
     */
    @Override
    @Transactional
    public List<BookingUpdateResponse> update(List<BookingUpdateRequest> payload) throws NotFoundException {
        final List<BookingUpdateResponse> response = new ArrayList<>();

        for (BookingUpdateRequest request : payload) {
            final Booking booking = bookingRepository.findById(request.getId()).orElseThrow(
                    () -> new NotFoundException(String.format("Booking with ID %d not found", request.getId()))
            );

            if (request.getStatus() != null) {
                booking.setStatus(request.getStatus());
            }

            booking.setUpdatedAt(OffsetDateTime.now());
            bookingRepository.save(booking);
            log.debug("booking updated");
            
            final Customer customer = booking.getCustomer();
            final Driver driver = booking.getDriver();

            response.add(BookingUpdateResponse.builder()
                    .id(booking.getId())
                    .estimatedCost(booking.getEstimatedCost())
                    .startLatitude(booking.getStartLatitude())
                    .startLongitude(booking.getStartLongitude())
                    .destLatitude(booking.getDestLatitude())
                    .destLongitude(booking.getDestLongitude())
                    .status(booking.getStatus())
                    .driver(DriverGetResponse.builder()
                            .id(driver.getId())
                            .email(driver.getEmail())
                            .phoneNumber(driver.getPhoneNumber())
                            .driverLicense(driver.getDriverLicense())
                            .createdAt(driver.getCreatedAt())
                            .updatedAt(driver.getUpdatedAt())
                            .build())
                    .customer(CustomerGetResponse.builder()
                            .id(customer.getId())
                            .name(customer.getName())
                            .email(customer.getEmail())
                            .phoneNumber(customer.getPhoneNumber())
                            .createdAt(customer.getCreatedAt())
                            .updatedAt(customer.getUpdatedAt())
                            .build())
                    .createdAt(booking.getCreatedAt())
                    .updatedAt(booking.getUpdatedAt())
                    .build());
        }
        return response;
    }

    @Override
    public List<BookingGetResponse> markAsCompleted(List<Long> ids) {
        final List<BookingGetResponse> response = new ArrayList<>();

        for (Long id : ids) {
            final Booking booking = bookingRepository.findById(id).orElseThrow(
                    () -> new NotFoundException(String.format("Booking with ID %d not found", id))
            );

            booking.setStatus(EBookingStatus.COMPLETED);
            booking.setUpdatedAt(OffsetDateTime.now());
            bookingRepository.save(booking);
            log.debug("booking marked as completed");

            final Driver driver = booking.getDriver();

            // Update the availability of the driver to available
            driver.setAvailability(EDriverAvailabilityStatus.AVAILABLE);
            driver.setUpdatedAt(OffsetDateTime.now());
            driverRepository.save(driver);

            response.add(generateBookingGetResponse(booking));
        }

        return response;
    }


    /**
     * Retrieves a list of bookings based on the provided parameters.
     *
     * @param bookingId Optional booking ID to retrieve a specific booking.
     * @param driverId Optional driver ID to retrieve bookings for a specific driver.
     * @param customerId Optional customer ID to retrieve bookings for a specific customer.
     * @return List of BookingGetResponse objects containing the booking details.
     * @throws NotFoundException if the booking, driver, or customer is not found.
     */
    @Override
    public List<BookingGetResponse> getBookings(Long bookingId, Long driverId, Long customerId) throws NotFoundException {
        if (bookingId != null) {
            final Booking booking = bookingRepository.findById(bookingId).orElseThrow(
                    () -> new NotFoundException(String.format("Booking with ID %d not found", bookingId))
            );
            return List.of(generateBookingGetResponse(booking));
        }

        if (driverId != null) {
            final List<Booking> bookings = bookingRepository.findByDriverId(driverId);
            return bookings.stream().map(this::generateBookingGetResponse).collect(Collectors.toList());
        }

        if (customerId != null) {
            final List<Booking> bookings = bookingRepository.findByCustomerId(customerId);
            return bookings.stream().map(this::generateBookingGetResponse).collect(Collectors.toList());
        }

        final List<Booking> bookings = bookingRepository.findAll();
        final List<BookingGetResponse> response = new ArrayList<>();
        for (Booking booking : bookings) {
            response.add(generateBookingGetResponse(booking));
        }
        return response;
    }

    /**
     * Generates a BookingGetResponse object from a Booking entity.
     *
     * @param booking Booking entity to generate the response from.
     * @return BookingGetResponse object containing the booking details.
     */
    private BookingGetResponse generateBookingGetResponse(Booking booking) {
        final Customer customer = booking.getCustomer();
        final Driver driver = booking.getDriver();
        final Vehicle vehicle = driver.getVehicles().get(0);
        final VehicleType vehicleType = vehicle.getVehicleType();

        return BookingGetResponse.builder()
                .id(booking.getId())
                .estimatedCost(booking.getEstimatedCost())
                .distanceInMeters(booking.getDistanceInMeters())
                .status(booking.getStatus())
                .driver(DriverGetResponse.builder()
                        .id(driver.getId())
                        .name(driver.getName())
                        .email(driver.getEmail())
                        .phoneNumber(driver.getPhoneNumber())
                        .driverLicense(driver.getDriverLicense())
                        .createdAt(driver.getCreatedAt())
                        .updatedAt(driver.getUpdatedAt())
                        .build())
                .customer(CustomerGetResponse.builder()
                        .id(customer.getId())
                        .name(customer.getName())
                        .email(customer.getEmail())
                        .phoneNumber(customer.getPhoneNumber())
                        .createdAt(customer.getCreatedAt())
                        .updatedAt(customer.getUpdatedAt())
                        .build())
                .vehicle(VehicleGetResponse.builder()
                        .id(vehicle.getId())
                        .manufacturer(vehicle.getManufacturer())
                        .model(vehicle.getModel())
                        .colour(vehicle.getColour())
                        .licensePlate(vehicle.getLicensePlate())
                        .createdAt(vehicle.getCreatedAt())
                        .updatedAt(vehicle.getUpdatedAt())
                        .vehicleType(VehicleTypeGetResponse.builder()
                                .id(vehicleType.getId())
                                .name(vehicleType.getName())
                                .pricePerMeter(vehicleType.getPricePerMeter())
                                .seatCount(vehicleType.getSeatCount())
                                .createdAt(vehicleType.getCreatedAt())
                                .updatedAt(vehicleType.getUpdatedAt())
                                .build())
                        .build())
                .createdAt(booking.getCreatedAt())
                .updatedAt(booking.getUpdatedAt())
                .build();
    }
}
