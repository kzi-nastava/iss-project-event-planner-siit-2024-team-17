package com.ftn.event_hopper.dtos.solutions;

import com.ftn.event_hopper.dtos.categories.SimpleCategoryDTO;
import com.ftn.event_hopper.dtos.eventTypes.SimpleEventTypeDTO;
import com.ftn.event_hopper.dtos.prices.SimplePriceDTO;
import com.ftn.event_hopper.models.shared.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProductForManagementDTO {
    private UUID id;
    private String name;
    private String description;
    private List<String> pictures;
    private boolean isAvailable;
    private boolean isVisible;
    private ProductStatus status;
    private SimplePriceDTO price;
    private SimpleCategoryDTO category;
    private List<SimpleEventTypeDTO> eventTypes;
}
