package com.ftn.event_hopper.dtos.users.eventOrganizer;

import com.ftn.event_hopper.dtos.users.person.UpdatePersonDTO;
import lombok.*;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class UpdateEventOrganizerDTO extends UpdatePersonDTO {
    private Collection<UUID> eventUUIDs;
    private Collection<UUID> productUUIDs;
}
