package com.ftn.event_hopper.models.notifications;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.users.Person;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Notification {
    private UUID id;
    private String content;
    private LocalDateTime timestamp;
    private Collection<Person> recipients;
    private Event event;
    private Product product;
}
