package com.ftn.event_hopper.dtos.invitations;


import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.models.shared.InvitationStatus;
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
public class CreatedInvitationDTO {
    private UUID id;
    private String targetEmail;
    private LocalDateTime timestamp;
    private InvitationStatus status;
    private String picture;
    private SimpleEventDTO event;
}
