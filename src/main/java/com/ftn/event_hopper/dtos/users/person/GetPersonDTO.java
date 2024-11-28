package com.ftn.event_hopper.dtos.users.person;

import com.ftn.event_hopper.models.users.PersonType;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetPersonDTO {
    private UUID id;
    private String name;
    private String surname;
    private String profilePicture;
    private String phoneNumber;
    private PersonType type;
    private UUID locationId;
    private Collection<UUID> notificationsIds;
    private Collection<UUID> attendingEventsIds;
    private Collection<UUID> favoriteEventsIds;
    private Collection<UUID> favoriteProductsIds;
    private UUID accountId;
}
