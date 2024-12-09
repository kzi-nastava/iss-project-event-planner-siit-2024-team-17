package com.ftn.event_hopper.services;

import com.ftn.event_hopper.dtos.events.GetEventDTO;
import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.mapper.EventDTOMapper;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.repositories.EventRepository;
import com.ftn.event_hopper.repositories.user.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EventDTOMapper eventDTOMapper;

    public List<SimpleEventDTO> findAll(){
        List<Event> events = eventRepository.findAll();
        return eventDTOMapper.fromEventListToSimpleDTOList(events);
    }

    public SimpleEventDTO findOne(UUID id) {
        Event event = eventRepository.findById(id).orElseGet(null);
        return eventDTOMapper.fromEventToSimpleDTO(event);

    }

    public Collection<SimpleEventDTO> findTop5(UUID userId) {

        Person person = personRepository.findById(userId).orElseGet(null);
        List<Event> top5Events = eventRepository.findTop5ByLocationCityAndPrivacyAndTimeAfterOrderByMaxAttendanceDesc(person.getLocation().getCity(), EventPrivacyType.PUBLIC, LocalDateTime.now());
        return eventDTOMapper.fromEventListToSimpleDTOList(top5Events);
    }
}
