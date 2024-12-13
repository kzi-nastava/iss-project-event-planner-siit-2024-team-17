package com.ftn.event_hopper.services.users;

import com.ftn.event_hopper.dtos.users.eventOrganizer.*;
import com.ftn.event_hopper.mapper.users.EventOrganizerDTOMapper;
import com.ftn.event_hopper.models.users.EventOrganizer;
import com.ftn.event_hopper.repositories.users.EventOrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventOrganizerService {
    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;
    @Autowired
    private EventOrganizerDTOMapper eventOrganizerDTOMapper;

    public SimpleEventOrganizerDTO findOne(UUID id) {
        return eventOrganizerDTOMapper.fromEventOrganizerToSimpleDTO(eventOrganizerRepository.findById(id).orElseGet(null));
    }

    public List<SimpleEventOrganizerDTO> findAll() {
        return eventOrganizerDTOMapper.fromEventOrganizerListToSimpleDTOList(eventOrganizerRepository.findAll());
    }

    public CreatedEventOrganizerDTO create(CreateEventOrganizerDTO organizerDTO){
        EventOrganizer organizer = eventOrganizerDTOMapper.fromCreateDTOToEventOrganizer(organizerDTO);
        this.save(organizer);
        return eventOrganizerDTOMapper.fromEventOrganizerToCreatedDTO(organizer);
    }

    public void save(EventOrganizer organizer) {
        eventOrganizerRepository.save(organizer);
    }

    public UpdatedEventOrganizerDTO update(UUID id, UpdateEventOrganizerDTO organizerDTO){
        EventOrganizer organizer = eventOrganizerRepository.findById(id).orElseGet(null);
        if(organizer != null){

        }
        return eventOrganizerDTOMapper.fromEventOrganizerToUpdatedDTO(organizer);
    }





}
