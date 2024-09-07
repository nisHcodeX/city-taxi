package com.citytaxi.city_taxi.models.entities;

import com.citytaxi.city_taxi.models.enums.EAccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Table(name = "account")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "account_sequence", sequenceName = "account_sequence", allocationSize = 1)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence")
    private Long id;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING)
    private EAccountStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
