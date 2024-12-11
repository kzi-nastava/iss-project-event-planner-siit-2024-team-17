package com.ftn.event_hopper.dtos.solutions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductDTO {
    private String name;
    private String description;
    private Collection<String> pictures;
    private boolean isAvailable;
    private boolean isVisible;
    private double basePrice;
    private double discount;
    private double finalPrice;
    private UUID serviceProviderId;
    private UUID categoryId;
    private Collection<UUID> eventTypesIds;
}
