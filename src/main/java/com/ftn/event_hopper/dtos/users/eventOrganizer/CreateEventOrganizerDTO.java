package com.ftn.event_hopper.dtos.users.eventOrganizer;


import com.ftn.event_hopper.dtos.users.person.CreatePersonDTO;
import com.ftn.event_hopper.models.solutions.Product;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CreateEventOrganizerDTO extends CreatePersonDTO {
    private Collection<UUID> eventUUIDs;
    private Collection<UUID> productUUIDs;
}
