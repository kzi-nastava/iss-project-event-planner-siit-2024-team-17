package com.ftn.event_hopper.models;

import com.ftn.event_hopper.shared.InvitationStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class Invitation {
    private UUID id;
    private String targetEmail;
    private LocalDateTime timestamp;
    private InvitationStatus status;
    private String picture;
    private Event event;

}
