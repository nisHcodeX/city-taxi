package com.citytaxi.city_taxi.util;

import com.citytaxi.city_taxi.models.entities.VehicleType;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Log4j2
@Service
public class CostGenerator {

    /**
     * Generates the cost based on the distance and the vehicle type.
     *
     * @param distance The distance traveled.
     * @param vehicleType The type of vehicle used.
     * @return The calculated cost based on the distance and the vehicle type's price per meter.
     */
    public double generateCost(double distance, VehicleType vehicleType) {
        final double cost = distance * vehicleType.getPricePerMeter();
        return BigDecimal.valueOf(cost).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
