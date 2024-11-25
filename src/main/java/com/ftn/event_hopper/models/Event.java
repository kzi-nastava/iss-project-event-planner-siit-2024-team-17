package com.ftn.event_hopper.models;

import com.ftn.event_hopper.models.shared.EventPrivacyType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private EventPrivacyType eventPrivacyType;
    private LocalDateTime startTime;
    private String picture;
    private EventType eventType;
    private AgendaActivity agendaActivity;
    private Location location;
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<Invitation> invitations = new ArrayList<Invitation>();
    private EventOrganizer eventOrganizer;
}
