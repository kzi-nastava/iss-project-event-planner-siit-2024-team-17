package com.ftn.event_hopper.models.events;

import com.ftn.event_hopper.models.agendaActivities.AgendaActivity;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.invitations.Invitation;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.users.EventOrganizer;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Event {

    private UUID id;
    private String name;
    private int maxAttendance;
    private String description;
    private EventPrivacyType privacy;
    private LocalDateTime time;
    private String picture;
    private EventType eventType;
    private Collection<AgendaActivity> agendaActivities;
    private Location location;
    private Collection<Invitation> invitations;
}
