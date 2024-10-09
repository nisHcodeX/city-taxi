package com.citytaxi.city_taxi.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Log4j2
@Service
public class DistanceCalculator {
    private static final double EARTH_RADIUS_KM = 6371;  // Earth radius in kilometers

    /**
     * Calculates the distance between two geographical points specified by their latitude and longitude.
     * This method uses the Haversine formula to compute the distance over the Earth's surface.
     *
     * @param lat1 Latitude of the first point.
     * @param lng1 Longitude of the first point.
     * @param lat2 Latitude of the second point.
     * @param lng2 Longitude of the second point.
     * @return The distance between the two points in meters.
     */
    public double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

//        final double distance = (EARTH_RADIUS_KM * c) * 1000;
        final double distance = (EARTH_RADIUS_KM * c);
        return BigDecimal.valueOf(distance).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
