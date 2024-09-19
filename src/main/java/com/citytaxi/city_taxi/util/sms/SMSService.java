package com.citytaxi.city_taxi.util.sms;

import com.citytaxi.city_taxi.exceptions.BadRequestException;
import com.citytaxi.city_taxi.exceptions.InternalServerException;
import com.citytaxi.city_taxi.models.dtos.sms.BookingDetailsSMSResponse;
import com.citytaxi.city_taxi.models.dtos.sms.DriverDetailsSMSResponse;
import com.citytaxi.city_taxi.models.dtos.sms.VehicleDetailsSMSResponse;
import com.citytaxi.city_taxi.models.entities.Booking;
import com.citytaxi.city_taxi.models.entities.Driver;
import com.citytaxi.city_taxi.models.entities.Vehicle;
import com.citytaxi.city_taxi.models.entities.VehicleType;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SMSService {
    @Value("${twilio.phone_number}")
    private String fromPhoneNumber;

    public void sendSMS(String toPhoneNumber, String payload) {
        try {
            Message.creator(
                    new PhoneNumber(toPhoneNumber),
                    new PhoneNumber(fromPhoneNumber),
                    payload
            ).create();
            log.debug("SMS sent to: {}", toPhoneNumber);
        } catch (ApiException ex) {
            if (ex.getMessage().contains("Invalid 'To' Phone Number")) {
                log.error("Invalid 'To' Phone Number: {}", toPhoneNumber);
                throw new BadRequestException(String.format("Invalid 'To' Phone Number: %s", toPhoneNumber));
            } else {
                log.error("Failed to send SMS to: {}", toPhoneNumber);
                throw new InternalServerException("Failed to send SMS");
            }
        }
    }

    /**
     * Generates a string representation of booking details for SMS.
     *
     * @param booking The booking entity containing the details to be included in the SMS.
     * @return A string representation of the booking details formatted for SMS.
     */
    public String generateBookingDetailsForSMS(Booking booking) {
        final Driver driver = booking.getDriver();
        final DriverDetailsSMSResponse driverDetails = DriverDetailsSMSResponse.builder()
                .name(driver.getName())
                .phoneNumber(driver.getPhoneNumber())
                .build();

        final Vehicle vehicle = booking.getDriver().getVehicles().get(0);
        final VehicleType vehicleType = vehicle.getVehicleType();
        final VehicleDetailsSMSResponse vehicleDetails = VehicleDetailsSMSResponse.builder()
                .manufacturer(vehicle.getManufacturer())
                .model(vehicle.getModel())
                .colour(vehicle.getColour())
                .licensePlate(vehicle.getLicensePlate())
                .vehicleType(vehicleType.getName())
                .build();

        BookingDetailsSMSResponse smsDetails = BookingDetailsSMSResponse.builder()
                .id(booking.getId())
                .estimatedCost(booking.getEstimatedCost())
                .distanceInMeters(booking.getDistanceInMeters())
                .driver(driverDetails)
                .vehicle(vehicleDetails)
                .build();

        return smsDetails.toString();
    }}
