package com.ftn.event_hopper.dtos.solutions;

import com.ftn.event_hopper.dtos.categories.SimpleCategoryDTO;
import com.ftn.event_hopper.dtos.eventTypes.SimpleEventTypeDTO;
import com.ftn.event_hopper.dtos.prices.SimplePriceDTO;
import com.ftn.event_hopper.models.shared.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ServiceManagementDTO {
    private UUID id;
    private String name;
    private String description;
    private SimplePriceDTO price;
    private ProductStatus status;
    private Collection<String> pictures;
    private boolean isVisible;
    private boolean isAvailable;
    private SimpleCategoryDTO category;
    private Collection<SimpleEventTypeDTO> eventTypes;

    private int durationMinutes;
    private int reservationWindowDays;
    private int cancellationWindowDays;
    private boolean isAutoAccept;
}
