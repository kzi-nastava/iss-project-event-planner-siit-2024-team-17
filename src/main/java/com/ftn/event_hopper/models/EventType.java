package com.ftn.event_hopper.models;

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
    private ArrayList<Event> events;
    private ArrayList<Category> categories;
}
