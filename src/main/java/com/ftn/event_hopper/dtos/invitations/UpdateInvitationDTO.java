package com.ftn.event_hopper.dtos.invitations;

import com.ftn.event_hopper.models.shared.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateInvitationDTO {
    private InvitationStatus status;
}
