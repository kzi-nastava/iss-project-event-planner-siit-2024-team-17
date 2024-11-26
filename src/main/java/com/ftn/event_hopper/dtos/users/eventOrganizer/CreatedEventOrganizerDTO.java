package com.ftn.event_hopper.dtos.users.eventOrganizer;

import com.ftn.event_hopper.dtos.users.person.CreatedPersonDTO;
import com.ftn.event_hopper.models.solutions.Product;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CreatedEventOrganizerDTO  extends CreatedPersonDTO {
    private Collection<UUID> eventUUIDs;
    private Collection<UUID> productUUIDs;
}
