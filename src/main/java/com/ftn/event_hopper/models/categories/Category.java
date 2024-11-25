package com.ftn.event_hopper.models.categories;

import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.shared.CategoryStatus;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Category {
    private UUID id;
    private String name;
    private String description;
    private CategoryStatus status;
    private Collection<EventType> eventTypes;
}
