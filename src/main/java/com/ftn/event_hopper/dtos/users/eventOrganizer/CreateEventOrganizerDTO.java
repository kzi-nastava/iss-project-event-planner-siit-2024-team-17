package com.ftn.event_hopper.dtos.users.eventOrganizer;


import com.ftn.event_hopper.dtos.users.person.CreatePersonDTO;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CreateEventOrganizerDTO extends CreatePersonDTO {
    private Collection<UUID> eventsIds;
    private Collection<UUID> productsIds;
}
