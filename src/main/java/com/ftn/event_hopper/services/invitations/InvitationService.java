package com.ftn.event_hopper.services.invitations;

import com.ftn.event_hopper.dtos.invitations.*;
import com.ftn.event_hopper.mapper.invitations.InvitationDTOMapper;
import com.ftn.event_hopper.models.emails.Email;
import com.ftn.event_hopper.models.invitations.Invitation;
import com.ftn.event_hopper.models.shared.InvitationStatus;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.repositories.invitations.InvitationRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.services.emails.EmailService;
import com.ftn.event_hopper.services.events.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InvitationService {

    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private InvitationDTOMapper invitationDTOMapper;
    @Autowired
    private EventService eventService;
    @Autowired
    private EmailService emailService;

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
        String subject = "Invitation to " + invitation.getEvent().getName();
        String body = "You are invited to " + invitation.getEvent().getName()
                +".\n" + "Location: " + invitation.getEvent().getLocation().getAddress()
                + "," + invitation.getEvent().getLocation().getCity() + "\n" +
                "Description: " + invitation.getEvent().getDescription()
                +"\n" + "Date: " + invitation.getEvent().getTime() + "\n"
                ;

        body += emailService.getAcceptInvitation(invitation.getId());

        Email email = new Email(invitation.getTargetEmail(), subject, body, invitation.getPicture(), invitation.getId());

        emailService.sendSimpleMail(email);
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
