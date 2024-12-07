package com.ftn.event_hopper.models.events;

import com.ftn.event_hopper.models.agendaActivities.AgendaActivity;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.invitations.Invitation;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column
    private int maxAttendance;

    @Column
    private String description;

    @Column(nullable = false)
    private EventPrivacyType privacy;

    @Column(nullable = false)
    private LocalDateTime time;

    @Column
    private String picture;

    private EventType eventType;
    private Collection<AgendaActivity> agendaActivities;


    private Location location;
    private Collection<Invitation> invitations;
}
