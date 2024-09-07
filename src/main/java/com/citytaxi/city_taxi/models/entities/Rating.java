package com.citytaxi.city_taxi.models.entities;

import com.citytaxi.city_taxi.models.enums.EAccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Table(name = "rating")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "rating_sequence", sequenceName = "rating_sequence", allocationSize = 1)
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rating_sequence")
    private Long id;
    private Integer rating;
    private OffsetDateTime createdAt;
}
