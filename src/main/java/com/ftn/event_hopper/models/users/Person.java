package com.ftn.event_hopper.models.users;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.notifications.Notification;
import com.ftn.event_hopper.models.shared.Location;
import com.ftn.event_hopper.models.solutions.Product;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Person {
    private UUID id;
    private String name;
    private String surname;
    private String profilePicture;
    private String phoneNumber;
    private PersonType type;
    private Location location;

    private Collection<Notification> notifications;
    private Collection<Event> attendingEvents;
    private Collection<Event> favoriteEvents;

    private Collection<Product> favoriteProducts;

    private Account account;
}

