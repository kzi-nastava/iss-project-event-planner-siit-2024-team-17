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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public Page<SimpleEventDTO> findAll(Pageable page, String city, UUID eventTypeId, LocalDateTime time, String searchContent) {
        Page<Event> eventsPage = eventRepository.findAll(page);
        List<Event> events = eventsPage.getContent();

        if (city != null) {
            events = events.stream()
                    .filter(event -> event.getLocation().getCity().equalsIgnoreCase(city))
                    .collect(Collectors.toList());
        }

        if (eventTypeId != null) {
            events = events.stream()
                    .filter(event -> event.getEventType().getId().equals(eventTypeId))
                    .collect(Collectors.toList());
        }

        if (time != null) {
            events = events.stream()
                    .filter(event -> event.getTime().equals(time))
                    .collect(Collectors.toList());
        }

        if (searchContent != null && !searchContent.isEmpty()) {
            events = events.stream()
                    .filter(event -> event.getName().toLowerCase().contains(searchContent.toLowerCase()))
                    .collect(Collectors.toList());
        }
        List<SimpleEventDTO> filteredEvents = eventDTOMapper.fromEventListToSimpleDTOList(events);


        return new PageImpl<>(filteredEvents, page, filteredEvents.size());
    }
}
