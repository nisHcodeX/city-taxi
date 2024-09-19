package com.citytaxi.city_taxi.models.entities;

import com.citytaxi.city_taxi.models.enums.EBookingStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Table(name = "booking")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "booking_sequence", sequenceName = "booking_sequence", allocationSize = 1)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_sequence")
    private Long id;
    private Double estimatedCost;
    private Double startLatitude;
    private Double startLongitude;
    private Double destLatitude;
    private Double destLongitude;
    private Double distanceInMeters;
    @Enumerated(EnumType.STRING)
    private EBookingStatus status;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Driver driver;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonBackReference
    private Customer customer;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "booking")
    @JsonManagedReference
    private Rating rating;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "booking")
    @JsonBackReference
    private Payment payment;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
