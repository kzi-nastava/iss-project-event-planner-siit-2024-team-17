package com.ftn.event_hopper.dtos.users.person;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.notifications.Notification;
import com.ftn.event_hopper.models.shared.Location;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.PersonType;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreatedPersonDTO {
    private UUID id;
    private String name;
    private String surname;
    private String profilePicture;
    private String phoneNumber;
    private PersonType type;
    private Location location;
    private Collection<UUID> notificationUUIDs;
    private Collection<UUID> attendingEventUUIDs;
    private Collection<UUID> favoriteEventUUIDs;
    private Collection<UUID> favoriteProductUUIDs;
    private UUID accountUUID;
}
