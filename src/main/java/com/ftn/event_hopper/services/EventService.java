package com.ftn.event_hopper.services;

import com.ftn.event_hopper.dtos.events.GetEventDTO;
import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.mapper.EventDTOMapper;
import com.ftn.event_hopper.mapper.solutions.ProductDTOMapper;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.repositories.EventRepository;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import com.ftn.event_hopper.repositories.user.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

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
    @Autowired
    private ProductDTOMapper productDTOMapper;
    @Autowired
    private ProductRepository productRepository;

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


        Specification<Event> specification = Specification.where(null);


        if (city != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("location").get("city"), city));
        }

        if (eventTypeId != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("eventType").get("id"), eventTypeId));
        }

        if (time != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("time"), time));
        }

        if (StringUtils.hasText(searchContent)) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + searchContent.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + searchContent.toLowerCase() + "%")
                    ));
        }


        return eventDTOMapper.fromEventPageToSimpleEventDTOPage(eventRepository.findAll(specification, page));

    }
}
