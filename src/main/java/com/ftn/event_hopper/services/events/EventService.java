package com.ftn.event_hopper.services.events;

import com.ftn.event_hopper.dtos.events.*;
import com.ftn.event_hopper.dtos.messages.ConversationPreviewDTO;
import com.ftn.event_hopper.mapper.events.AgendaMapper;
import com.ftn.event_hopper.mapper.events.EventDTOMapper;
import com.ftn.event_hopper.models.blocks.Block;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.events.AgendaActivity;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.EventOrganizer;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.repositories.blocking.BlockingRepository;
import com.ftn.event_hopper.repositories.eventTypes.EventTypeRepository;
import com.ftn.event_hopper.repositories.events.AgendaActivityRepository;
import com.ftn.event_hopper.repositories.events.EventRepository;
import com.ftn.event_hopper.repositories.locations.LocationRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.repositories.users.EventOrganizerRepository;
import com.ftn.event_hopper.repositories.users.PersonRepository;
import jakarta.persistence.criteria.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.stream.Collectors;
import com.ftn.event_hopper.models.events.EventRating;



@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventTypeRepository eventTypeRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EventDTOMapper eventDTOMapper;
    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private AgendaActivityRepository agendaActivityRepository;
    @Autowired
    private AgendaMapper agendaMapper;
    @Autowired
    private BlockingRepository blockingRepository;


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


    public GetEventAgendasDTO getEventAgendas(UUID id) {
        Event event = eventRepository.findById(id).orElse(null);
        if(event == null){
            return null;
        }
        Set<AgendaActivity> activities = event.getAgendaActivities();
        Logger logger = LoggerFactory.getLogger(EventService.class);
        for(AgendaActivity activity : activities){
            logger.info("Activity: " + activity);
        }

        return agendaMapper.mapToGetEventAgendasDTO(activities);
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

        //check if they are blocked
        EventOrganizer od = eventOrganizerRepository.findByEventsContaining(event).orElse(null);
        if (od != null){
            Account organizerAccount = accountRepository.findByPersonId(od.getId()).orElse(null);
            if (organizerAccount != null){
                if (account.getType() == PersonType.SERVICE_PROVIDER){
                    Block block = blockingRepository.findByWhoAndBlocked(account, organizerAccount).orElse(null);
                    if (block != null){
                        throw new RuntimeException("Content is not available");
                    }
                } else if (account.getType() == PersonType.AUTHENTICATED_USER) {
                    Block odBlocked = blockingRepository.findByWhoAndBlocked(account, organizerAccount).orElse(null);
                    Block akBlocked = blockingRepository.findByWhoAndBlocked(organizerAccount, account).orElse(null);

                    if (odBlocked != null || akBlocked != null){
                        throw new RuntimeException("Content is not available");
                    }

                }

            }
        }

        if(account.getType() == PersonType.EVENT_ORGANIZER){
            EventOrganizer eventOrganizer = eventOrganizerRepository.findById(account.getPerson().getId()).orElse(null);
            eventDTO.setEventOrganizerLoggedIn(eventOrganizer.getEvents().contains(event));
        }

        ConversationPreviewDTO conversation = null;
        if (person != null) {
            eventDTO.setFavorite(person.getFavoriteEvents().contains(event));

            if (person.getAttendingEvents().contains(event)) {
                EventOrganizer organizer = eventOrganizerRepository.findByEventsContaining(event).orElse(null);
                Account organizersAccount = accountRepository.findByPersonId(organizer.getId()).orElse(null);
                if (organizersAccount != null && !organizersAccount.getId().equals(account.getId())) {
                    conversation = new ConversationPreviewDTO();
                    conversation.setUsername(organizersAccount.getUsername());
                    conversation.setName(organizer.getName());
                    conversation.setSurname(organizer.getSurname());
                    conversation.setProfilePictureUrl(organizer.getProfilePicture());
                }
            }
        }

        eventDTO.setConversationInitialization(conversation);

        return eventDTO;
    }

    public int getMaximumAttendance(UUID id){
        Event event = eventRepository.findById(id).orElseGet(null);
        return event.getMaxAttendance();
    }

    public Collection<SimpleEventDTO> findTop5(UUID userId) {
        Account account = accountRepository.findById(userId).orElseGet(null);
        Person person = account.getPerson();
        List<Event> events = eventRepository.findByLocationCityAndPrivacyAndTimeAfterOrderByMaxAttendanceDesc(person.getLocation().getCity(), EventPrivacyType.PUBLIC, LocalDateTime.now());
        List<Event> filteredEvents = new ArrayList<>();
        int counter = 0;
        for (Event event : events) {
            if(counter == 5){
                break;
            }

            EventOrganizer eventOrganizer = eventOrganizerRepository.findByEventsContaining(event).orElse(null);
            if(eventOrganizer != null){
                Account organizersAccount = accountRepository.findByPersonId(person.getId()).orElse(null);
                if (organizersAccount != null){
                    Block block = blockingRepository.findByWhoAndBlocked(account,organizersAccount).orElse(null);
                    if (block != null){
                        continue;
                    }
                }
            }
            filteredEvents.add(event);
            counter++;
        }

        return eventDTOMapper.fromEventListToSimpleDTOList(filteredEvents);
    }


    public Event create(CreateEventDTO eventDTO) {
        List<CreateAgendaActivityDTO> activitiesDTO = eventDTO.getAgendaActivities();

        Location eventLocation = new Location();
        eventLocation.setAddress(eventDTO.getLocation().getAddress());
        eventLocation.setCity(eventDTO.getLocation().getCity());
        eventLocation.setLongitude(0.0);
        eventLocation.setLatitude(0.0);
        locationRepository.save(eventLocation);

        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setPicture(eventDTO.getPicture());
        if(eventDTO.getEventTypeId() != null){
            EventType eventType = eventTypeRepository.findById(eventDTO.getEventTypeId()).orElse(null);
            event.setEventType(eventType);
        }
        event.setPrivacy(eventDTO.getEventPrivacyType());
        event.setMaxAttendance(eventDTO.getMaxAttendance());
        event.setTime(eventDTO.getTime());

        event.setLocation(eventLocation);

        Set<AgendaActivity> activities = new HashSet<>();
        for(CreateAgendaActivityDTO activityDTO : activitiesDTO){
            AgendaActivity activity = new AgendaActivity();
            activity.setName(activityDTO.getName());
            activity.setDescription(activityDTO.getDescription());
            activity.setStartTime(activityDTO.getStartTime());
            activity.setEndTime(activityDTO.getEndTime());
            activity.setLocationName(activityDTO.getLocationName());
            activities.add(activity);
            agendaActivityRepository.save(activity);
        }

        event.setAgendaActivities(activities);
        eventRepository.save(event);
        return event;
    }


    public List<RatingTimeSeriesDTO> getAverageRatingsOverTime(UUID eventId) {
        Optional<Event> eventOpt = eventRepository.findById(eventId);
        if(eventOpt.isEmpty()){
            return new ArrayList<>();
        }
        Event event = eventOpt.get();

        return event.getRatings().stream()
                .collect(Collectors.groupingBy(
                        rating -> rating.getTimestamp().toLocalDate().atStartOfDay(), // group by day
                        Collectors.averagingInt(EventRating::getValue)
                ))
                .entrySet().stream()
                .map(entry -> new RatingTimeSeriesDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(RatingTimeSeriesDTO::getTimestamp))
                .collect(Collectors.toList());
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

        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() != null
                && (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Account)){

                Account loggedAccount = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

                if (loggedAccount.getType() != PersonType.EVENT_ORGANIZER){


                specification = specification.and((root, query, criteriaBuilder) -> {


                Root<EventOrganizer> eventOrganizerRoot = query.from(EventOrganizer.class);
                Join<EventOrganizer,Event> eventJoin = eventOrganizerRoot.join("events", JoinType.INNER);

                Subquery<Account> accountSubquery = query.subquery(Account.class);
                Root<Account> accountRoot = accountSubquery.from(Account.class);
                accountSubquery.select(accountRoot)
                        .where(criteriaBuilder.equal(accountRoot.get("person"), eventOrganizerRoot));


                Subquery<Long> blockSubquery = query.subquery(Long.class);
                Root<Block> blockRoot = blockSubquery.from(Block.class);
                //if pup or ak blocks od
                if ( loggedAccount.getType() == PersonType.SERVICE_PROVIDER ){
                    blockSubquery.select(criteriaBuilder.count(blockRoot))
                            .where(
                                    criteriaBuilder.equal(blockRoot.get("who"), loggedAccount),
                                    criteriaBuilder.equal(blockRoot.get("blocked"), accountSubquery)
                            );
                } else if ( loggedAccount.getType() == PersonType.AUTHENTICATED_USER ){

                    blockSubquery.select(criteriaBuilder.count(blockRoot))
                            .where(criteriaBuilder.or(
                                    criteriaBuilder.and(
                                            criteriaBuilder.equal(blockRoot.get("who"), accountSubquery),
                                            criteriaBuilder.equal(blockRoot.get("blocked"), loggedAccount)
                                    ),
                                    criteriaBuilder.and(
                                            criteriaBuilder.equal(blockRoot.get("who"), loggedAccount),
                                            criteriaBuilder.equal(blockRoot.get("blocked"), accountSubquery)
                                    )
                            ));

                }

                return criteriaBuilder.and(
                        criteriaBuilder.equal(root, eventJoin),
                        criteriaBuilder.equal(blockSubquery,0)
                );

            });

                }
        }

        Pageable pageableWithSort = PageRequest.of(page.getPageNumber(), page.getPageSize(), sort);

        Page<Event> filteredEvents = eventRepository.findAll(specification, pageableWithSort);

        Page<SimpleEventDTO> all = eventDTOMapper.fromEventPageToSimpleEventDTOPage(filteredEvents);


        return all;
    }

}
