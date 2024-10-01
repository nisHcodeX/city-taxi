package com.citytaxi.city_taxi.models.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Table(name = "telephone_operator")
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "telephone_operator_sequence", sequenceName = "telephone_operator_sequence", allocationSize = 1)
public class TelephoneOperator {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "telephone_operator_sequence")
    private Long id;
    private String name;
    @Column(unique = true)
    private String phoneNumber;
    @Column(unique = true)
    private String email;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonManagedReference
    private Account account;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}
