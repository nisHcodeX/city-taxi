package com.citytaxi.city_taxi.models.dtos.sms;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailsSMSResponse {
    private Long id;
    private Double estimatedCost;
    private Double distanceInMeters;
    private DriverDetailsSMSResponse driver;
    private VehicleDetailsSMSResponse vehicle;

    /**
     * Returns a string representation of the booking details formatted for SMS.
     *
     * @return A formatted string containing the booking details.
     */
    @Override
    public String toString() {
        return String.format(
                """
                Your booking was confirmed! Here are the details:
                
                Booking id: %d
                Estimated cost: %.2f
                Total distance in Km: %.2f
                
                Driver name: %s
                Driver phoneNumber: %s
                
                Vehicle: %s %s
                Colour: %s
                License plate number: %s
                """,
                id,
                estimatedCost,
                distanceInMeters,
                driver.getName(),
                driver.getPhoneNumber(),
                vehicle.getManufacturer(),
                vehicle.getModel(),
                vehicle.getColour(),
                vehicle.getLicensePlate()
        );
    }
}
