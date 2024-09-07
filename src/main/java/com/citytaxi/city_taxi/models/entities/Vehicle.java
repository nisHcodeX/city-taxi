package com.citytaxi.city_taxi.models.entities;

import com.citytaxi.city_taxi.models.enums.EDriverAvailabilityStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Table(name = "vehicle")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "vehicle_sequence", sequenceName = "vehicle_sequence", allocationSize = 1)
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_sequence")
    private Long id;
    @Column(unique = true)
    private String manufacturer;
    private String model;
    private String colour;
    private String licensePlate;
    private Integer seatCount;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_type_id", referencedColumnName = "id")
    private VehicleType vehicleType;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
