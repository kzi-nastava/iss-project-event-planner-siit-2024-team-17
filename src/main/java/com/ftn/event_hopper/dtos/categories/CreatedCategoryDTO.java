package com.ftn.event_hopper.dtos.categories;

import com.ftn.event_hopper.dtos.eventTypes.SimpleEventTypeDTO;
import com.ftn.event_hopper.models.shared.CategoryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreatedCategoryDTO {
    private UUID id;
    private String name;
    private String description;
    private CategoryStatus status;
    private Collection<SimpleEventTypeDTO> eventTypes;
}
