package com.ftn.event_hopper.dtos.events;

import com.ftn.event_hopper.models.invitations.Invitation;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import com.ftn.event_hopper.models.solutions.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GetEventDTO {

    private UUID id;
    private String name;
    private int maxAttendance;
    private String description;
    private EventPrivacyType eventPrivacyType;
    private LocalDateTime time;
    private String picture;
    private UUID eventTypeId;
    private UUID agendaActivityId;
    private UUID locationId;
    private ArrayList<UUID> productsIds = new ArrayList<UUID>();
    private ArrayList<UUID> invitationsIds = new ArrayList<UUID>();
    private UUID eventOrganizerId;

}
