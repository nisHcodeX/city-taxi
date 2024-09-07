package com.citytaxi.city_taxi.models.entities;

import com.citytaxi.city_taxi.models.enums.EDriverAvailabilityStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Table(name = "vehicle_type")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "vehicle_type_sequence", sequenceName = "vehicle_type_sequence", allocationSize = 1)
public class VehicleType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_type_sequence")
    private Long id;
    @Column(unique = true)
    private String name;
    private Double pricePerMeter;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
