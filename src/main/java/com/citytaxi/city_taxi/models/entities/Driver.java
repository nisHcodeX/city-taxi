package com.citytaxi.city_taxi.models.entities;

import com.citytaxi.city_taxi.models.enums.EDriverAvailabilityStatus;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.List;

@Table(name = "driver")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "driver_sequence", sequenceName = "driver_sequence", allocationSize = 1)
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_sequence")
    private Long id;
    @Column(unique = true)
    private String nic;
    private String name;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phoneNumber;
    @Column(unique = true)
    private String driverLicense;
    @Enumerated(EnumType.STRING)
    private EDriverAvailabilityStatus availability;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonManagedReference
    private Account account;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private List<Vehicle> vehicles;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private List<Booking> bookings;
    private Double longitude;
    private Double latitude;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
