package com.ftn.event_hopper.models.eventTypes;

import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.events.Event;
import lombok.*;

import java.util.ArrayList;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class EventType {
    private UUID id;
    private String name;
    private String description;
    private boolean isDeactivated;
}
