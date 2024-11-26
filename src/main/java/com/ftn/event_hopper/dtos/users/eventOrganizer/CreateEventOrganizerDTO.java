package com.ftn.event_hopper.dtos.users.eventOrganizer;


import com.ftn.event_hopper.dtos.users.PersonDTO;
import com.ftn.event_hopper.models.solutions.Product;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CreateEventOrganizerDTO extends PersonDTO {
    private Collection<String> eventUUIDs;
    private Collection<Product> productUUIDs;
}
