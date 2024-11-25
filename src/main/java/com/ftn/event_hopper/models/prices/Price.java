package com.ftn.event_hopper.models.prices;

import com.ftn.event_hopper.models.solutions.Product;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Price {
    private UUID id;
    private double basePrice;
    private double discount;
    private double finalPrice;
    private LocalDateTime timestamp;
    private Product product;
}
