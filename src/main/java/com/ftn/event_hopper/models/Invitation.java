package com.ftn.event_hopper.models;

import com.ftn.event_hopper.models.shared.InvitationStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Invitation {
    private UUID id;
    private String targetEmail;
    private LocalDateTime timestamp;
    private InvitationStatus status;
    private String picture;
    private Event event;

}
