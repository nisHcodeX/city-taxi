package com.citytaxi.city_taxi.models.entities;

import com.citytaxi.city_taxi.models.enums.EBookingStatus;
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
    private String startDestination;
    private String endDestination;
    @Enumerated(EnumType.STRING)
    private EBookingStatus status;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Driver driver;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Customer customer;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "rating_id", referencedColumnName = "id")
    private Rating rating;
    private OffsetDateTime createdAt;
}
