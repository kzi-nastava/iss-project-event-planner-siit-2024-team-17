package com.ftn.event_hopper.mapper.user;

import com.ftn.event_hopper.dtos.users.eventOrganizer.*;
import com.ftn.event_hopper.models.users.EventOrganizer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventOrganizerDTOMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public EventOrganizerDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public SimpleEventOrganizerDTO fromEventOrganizerToSimpleDTO(EventOrganizer eventOrganizer) {
        return modelMapper.map(eventOrganizer, SimpleEventOrganizerDTO.class);
    }

    public CreateEventOrganizerDTO fromEventOrganizerToCreateDTO(EventOrganizer eventOrganizer) {
        return modelMapper.map(eventOrganizer, CreateEventOrganizerDTO.class);
    }

    public CreatedEventOrganizerDTO fromEventOrganizerToCreatedDTO(EventOrganizer eventOrganizer) {
        return modelMapper.map(eventOrganizer, CreatedEventOrganizerDTO.class);
    }

    public UpdatedEventOrganizerDTO fromEventOrganizerToUpdatedDTO(EventOrganizer eventOrganizer) {
        return modelMapper.map(eventOrganizer, UpdatedEventOrganizerDTO.class);
    }

    public UpdateEventOrganizerDTO fromEventOrganizerToUpdateDTO(EventOrganizer eventOrganizer) {
        return modelMapper.map(eventOrganizer, UpdateEventOrganizerDTO.class);
    }

    public EventOrganizer fromSimpleDTOToEventOrganizer(SimpleEventOrganizerDTO dto) {
        return modelMapper.map(dto, EventOrganizer.class);
    }

    public EventOrganizer fromCreateDTOToEventOrganizer(CreateEventOrganizerDTO dto) {
        return modelMapper.map(dto, EventOrganizer.class);
    }

    public List<SimpleEventOrganizerDTO> fromEventOrganizerListToSimpleDTOList(List<EventOrganizer> organizers) {
        return organizers.stream()
                .map(this::fromEventOrganizerToSimpleDTO)
                .collect(Collectors.toList());
    }


}
