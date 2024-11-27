package com.ftn.event_hopper.dtos.prices;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreatedPriceDTO {
    private UUID id;
    private double basePrice;
    private double discount;
    private double finalPrice;
    private UUID productId;
    private LocalDateTime timestamp;
}
