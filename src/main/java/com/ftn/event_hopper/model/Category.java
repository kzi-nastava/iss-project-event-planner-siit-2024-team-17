package com.ftn.event_hopper.model;

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
