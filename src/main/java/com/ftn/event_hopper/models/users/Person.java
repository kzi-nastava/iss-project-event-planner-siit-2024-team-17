package com.ftn.event_hopper.models.users;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.notifications.Notification;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.solutions.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "persons")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String name;
    private String surname;
    private String profilePicture;
    private String phoneNumber;
    private PersonType type;
    private Location location;

    private List<Notification> notifications = new ArrayList<>();
    private Set<Event> attendingEvents = new HashSet<>();
    private Set<Event> favoriteEvents = new HashSet<>();

    private Set<Product> favoriteProducts = new HashSet<>();
}

