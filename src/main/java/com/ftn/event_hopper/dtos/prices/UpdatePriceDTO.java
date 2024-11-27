package com.ftn.event_hopper.dtos.prices;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePriceDTO {
    private double basePrice;
    private double discount;
    private UUID productId;
}
