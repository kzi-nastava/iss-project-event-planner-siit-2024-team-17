package com.ftn.event_hopper.dtos.users.eventOrganizer;

import com.ftn.event_hopper.dtos.users.PersonDTO;
import com.ftn.event_hopper.models.solutions.Product;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class GetEventOrganizerDTO extends PersonDTO {
    private UUID id;
    private Collection<String> eventUUIDs;
    private Collection<Product> productUUIDs;
}
