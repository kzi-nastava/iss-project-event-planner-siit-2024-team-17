package com.ftn.event_hopper.models.users;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.solutions.Product;
import lombok.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class EventOrganizer extends Person{
    private Set<Event> events = new HashSet<>();
}
