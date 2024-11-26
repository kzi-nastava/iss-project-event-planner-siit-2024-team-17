package com.ftn.event_hopper.dtos.prices;

import com.ftn.event_hopper.models.solutions.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreatePriceDTO {
    private double basePrice;
    private double discount;
    private UUID productId;
}
