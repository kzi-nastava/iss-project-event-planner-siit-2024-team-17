package com.ftn.event_hopper.services.events;

import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.events.SinglePageEventDTO;
import com.ftn.event_hopper.mapper.events.EventDTOMapper;
import com.ftn.event_hopper.mapper.solutions.ProductDTOMapper;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.EventOrganizer;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.repositories.events.EventRepository;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.repositories.users.EventOrganizerRepository;
import com.ftn.event_hopper.repositories.users.PersonRepository;
import jakarta.persistence.criteria.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EventDTOMapper eventDTOMapper;
    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;

    public List<SimpleEventDTO> findAll(){
        List<Event> events = eventRepository.findAll();
        return eventDTOMapper.fromEventListToSimpleDTOList(events);
    }

    public List<SimpleEventDTO> findForOrganizer(){
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null
                || !(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Account)) {
            return null;
        }

        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account.getType() != PersonType.EVENT_ORGANIZER){
            return null;
        }
        EventOrganizer eventOrganizer = eventOrganizerRepository.findById(account.getPerson().getId()).orElse(null);
        Set<Event> events = eventOrganizer.getEvents();
        if(events == null){
            return null;
        }
        return eventDTOMapper.fromEventSetToSimpleDTOList(events);
    }


    public SinglePageEventDTO findOne(UUID id) {
        Event event = eventRepository.findById(id).orElseGet(null);
        SinglePageEventDTO eventDTO = eventDTOMapper.fromEventToSinglePageDTO(event);

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null
                || !(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Account)) {
            eventDTO.setEventOrganizerLoggedIn(false);
            return eventDTO;
        }

        //someone is logged in
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Person person = personRepository.findById(account.getPerson().getId()).orElse(null);

        if(account.getType() == PersonType.EVENT_ORGANIZER){
            EventOrganizer eventOrganizer = eventOrganizerRepository.findById(account.getPerson().getId()).orElse(null);
            eventDTO.setEventOrganizerLoggedIn(eventOrganizer.getEvents().contains(event));
        }

        if (person != null) {
            eventDTO.setFavorite(person.getFavoriteEvents().contains(event));
        }
        return eventDTO;
    }

    public Collection<SimpleEventDTO> findTop5(UUID userId) {

        Person person = personRepository.findById(userId).orElseGet(null);
        List<Event> top5Events = eventRepository.findTop5ByLocationCityAndPrivacyAndTimeAfterOrderByMaxAttendanceDesc(person.getLocation().getCity(), EventPrivacyType.PUBLIC, LocalDateTime.now());
        return eventDTOMapper.fromEventListToSimpleDTOList(top5Events);
    }


    public Page<SimpleEventDTO> findAll(
            Pageable page,
            String city,
            UUID eventTypeId,
            String time,
            String searchContent,
            String sortField,
            String sortDirection
            ) {


        Specification<Event> specification = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.greaterThan(root.get("time"), LocalDateTime.now()),
                        criteriaBuilder.equal(root.get("privacy"), 1)
                ));

        if (city != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("location").get("city"), city));
        }

        if (eventTypeId != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("eventType").get("id"), eventTypeId));
        }

        if (time != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDate parsedDate = LocalDate.parse(time, formatter);

            specification = specification.and((root, query, criteriaBuilder) -> {
                Expression<LocalDate> eventDate = criteriaBuilder.function(
                        "date", LocalDate.class, root.get("time"));
                return criteriaBuilder.equal(eventDate, parsedDate);
            });
        }




        if (StringUtils.hasText(searchContent)) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchContent.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + searchContent.toLowerCase() + "%")
                    ));
        }

        Sort sort = Sort.unsorted();
        if (sortField != null && !sortField.isEmpty()) {
            Sort.Direction direction = Sort.Direction.ASC; // Podrazumevani smer sortiranja je rastući
            if ("desc".equalsIgnoreCase(sortDirection)) {
                direction = Sort.Direction.DESC;
            }

            sort = Sort.by(direction, sortField); // Sortira po izabranom polju i smeru
        }

        Pageable pageableWithSort = PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);

        Page<Event> filteredEvents = eventRepository.findAll(specification, pageableWithSort);

        Page<SimpleEventDTO> all = eventDTOMapper.fromEventPageToSimpleEventDTOPage(filteredEvents);


        return all;
    }

}
