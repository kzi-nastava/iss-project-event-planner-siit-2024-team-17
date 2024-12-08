package com.ftn.event_hopper.dtos.users.eventOrganizer;

import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.users.person.CreatedPersonDTO;
import lombok.*;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class CreatedEventOrganizerDTO  extends CreatedPersonDTO {
}
