package com.ftn.event_hopper.mapper.invitations;

import com.ftn.event_hopper.dtos.categories.SimpleCategoryDTO;
import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.invitations.CreateInvitationDTO;
import com.ftn.event_hopper.dtos.invitations.CreatedInvitationDTO;
import com.ftn.event_hopper.dtos.invitations.InvitationDTO;
import com.ftn.event_hopper.dtos.invitations.UpdatedInvitationDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.mapper.events.EventDTOMapper;
import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.invitations.Invitation;
import com.ftn.event_hopper.models.solutions.Product;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvitationDTOMapper  {
    private final ModelMapper modelMapper;
    private final EventDTOMapper eventDTOMapper;

    public InvitationDTOMapper(ModelMapper modelMapper, EventDTOMapper eventDTOMapper) {
        this.modelMapper = modelMapper;
        this.eventDTOMapper = eventDTOMapper;
        configureMappings();
    }

    public void configureMappings() {
        Converter<Event, SimpleEventDTO> invitationConverter = context ->
                eventDTOMapper.fromEventToSimpleDTO(context.getSource());

        // Custom mapping for Product -> ProductDTO
        modelMapper.typeMap(Invitation.class, InvitationDTO.class)
                .addMappings(mapper -> mapper.using(invitationConverter)
                        .map(Invitation::getEvent, InvitationDTO::setEvent));
    }

    public InvitationDTO fromInvitationToInvitationDTO(Invitation invitation) {
        return modelMapper.map(invitation, InvitationDTO.class);
    }

    public List<InvitationDTO> fromInvitationListToInvitationDTOList(List<Invitation> invitations) {
        return invitations.stream()
                .map(this::fromInvitationToInvitationDTO)
                .collect(Collectors.toList());
    }

    public CreatedInvitationDTO fromInvitationToCreatedInvitationDTO(Invitation invitation) {
        return modelMapper.map(invitation, CreatedInvitationDTO.class);
    }

    public Invitation fromCreateInvitationDTOtoInvitation(CreateInvitationDTO createInvitationDTO) {
        return modelMapper.map(createInvitationDTO, Invitation.class);
    }

    public UpdatedInvitationDTO fromInvitationToUpdatedInvitationDTO(Invitation invitation) {
        return modelMapper.map(invitation, UpdatedInvitationDTO.class);
    }
}
