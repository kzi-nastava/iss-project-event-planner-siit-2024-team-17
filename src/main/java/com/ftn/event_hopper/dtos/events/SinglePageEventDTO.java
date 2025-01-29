package com.ftn.event_hopper.dtos.events;

import com.ftn.event_hopper.dtos.eventTypes.SimpleEventTypeDTO;
import com.ftn.event_hopper.dtos.location.SimpleLocationDTO;
import com.ftn.event_hopper.dtos.messages.ConversationPreviewDTO;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SinglePageEventDTO {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime time;
    private String picture;
    private SimpleEventTypeDTO eventType;
    private SimpleLocationDTO location;
    private EventPrivacyType privacy;
    private boolean eventOrganizerLoggedIn;
    private boolean favorite;
    private ConversationPreviewDTO conversationInitialization;
}
