package com.ftn.event_hopper.services.invitations;

import com.ftn.event_hopper.dtos.invitations.*;
import com.ftn.event_hopper.mapper.invitations.InvitationDTOMapper;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.invitations.Invitation;
import com.ftn.event_hopper.models.shared.InvitationStatus;
import com.ftn.event_hopper.repositories.invitations.InvitationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private InvitationDTOMapper invitationDTOMapper;

    public List<InvitationDTO> findAll() {
        List<Invitation> invitations = invitationRepository.findAll();
         return invitationDTOMapper.fromInvitationListToInvitationDTOList(invitations);
    }

    public InvitationDTO findById(UUID id) {
        Invitation invitation = invitationRepository.findById(id).orElse(null);
        return invitationDTOMapper.fromInvitationToInvitationDTO(invitation);
    }

    public CreatedInvitationDTO create(CreateInvitationDTO invitationDTO) {
        Invitation invitation = invitationDTOMapper.fromCreateInvitationDTOtoInvitation(invitationDTO);
        invitation.setStatus(InvitationStatus.PENDING);
        invitation.setTimestamp(LocalDateTime.now());
        this.save(invitation);
        return invitationDTOMapper.fromInvitationToCreatedInvitationDTO(invitation);
    }

    public UpdatedInvitationDTO update(UUID id, UpdateInvitationDTO invitationDTO) {
        Invitation invitation = invitationRepository.findById(id).orElse(null);
        if(invitation != null) {
            invitation.setStatus(invitationDTO.getStatus());
            this.save(invitation);
        }
        return invitationDTOMapper.fromInvitationToUpdatedInvitationDTO(invitation);

    }

    public Invitation save(Invitation invitation) {
        return invitationRepository.save(invitation);
    }
}
