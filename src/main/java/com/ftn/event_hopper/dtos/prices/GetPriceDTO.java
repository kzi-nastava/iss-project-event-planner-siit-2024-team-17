package com.ftn.event_hopper.dtos.prices;

import com.ftn.event_hopper.models.solutions.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class GetPriceDTO {
    private UUID id;
    private double basePrice;
    private double discount;
    private double finalPrice;
    private LocalDateTime timestamp;
    private UUID productId;
}
