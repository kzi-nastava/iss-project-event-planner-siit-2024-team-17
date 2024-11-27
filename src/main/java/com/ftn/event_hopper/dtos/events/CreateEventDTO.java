package com.ftn.event_hopper.dtos.events;

import com.ftn.event_hopper.models.invitations.Invitation;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.users.EventOrganizer;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CreateEventDTO {
    private String name;
    private int maxAttendance;
    private String description;
    private EventPrivacyType eventPrivacyType;
    private LocalDateTime startTime;
    private String picture;
    private UUID eventTypeId;
    private UUID agendaActivityId;
    private UUID locationId;
    private ArrayList<Product> products = new ArrayList<Product>();
    private ArrayList<Invitation> invitations = new ArrayList<Invitation>();
    private UUID eventOrganizerId;
}
