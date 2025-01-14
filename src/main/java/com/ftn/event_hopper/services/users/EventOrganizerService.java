package com.ftn.event_hopper.services.users;

import com.ftn.event_hopper.dtos.users.eventOrganizer.*;
import com.ftn.event_hopper.mapper.users.EventOrganizerDTOMapper;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.users.EventOrganizer;
import com.ftn.event_hopper.repositories.events.EventRepository;
import com.ftn.event_hopper.repositories.users.EventOrganizerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventOrganizerService {
    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;
    @Autowired
    private EventOrganizerDTOMapper eventOrganizerDTOMapper;
    @Autowired
    private EventRepository eventRepository;

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


    public void addEvent(UUID organizerId, UUID eventId){
        EventOrganizer organizer = eventOrganizerRepository.findById(organizerId).orElseGet(null);
        if(organizer != null){
            Optional<Event> event = eventRepository.findById(eventId);
            if(event.isPresent()){
                organizer.getEvents().add(event.get());
                this.save(organizer);
            }
        }

    }


}
