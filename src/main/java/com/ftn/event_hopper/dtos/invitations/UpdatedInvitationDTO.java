package com.ftn.event_hopper.dtos.invitations;

import com.ftn.event_hopper.models.shared.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedInvitationDTO {
    private UUID id;
    private InvitationStatus status;
}
