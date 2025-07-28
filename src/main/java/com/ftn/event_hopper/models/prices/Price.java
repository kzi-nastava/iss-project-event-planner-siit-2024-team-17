package com.ftn.event_hopper.models.prices;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private double basePrice;

    @Column(nullable = false)
    private double discount;

    @Column(nullable = false)
    private double finalPrice;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
