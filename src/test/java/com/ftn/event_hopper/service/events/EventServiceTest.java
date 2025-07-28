package com.ftn.event_hopper.service.events;

import com.ftn.event_hopper.dtos.events.CreateAgendaActivityDTO;
import com.ftn.event_hopper.dtos.events.CreateEventDTO;
import com.ftn.event_hopper.dtos.location.CreateLocationDTO;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.events.AgendaActivity;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.repositories.eventTypes.EventTypeRepository;
import com.ftn.event_hopper.repositories.events.AgendaActivityRepository;
import com.ftn.event_hopper.repositories.events.EventRepository;
import com.ftn.event_hopper.repositories.locations.LocationRepository;
import com.ftn.event_hopper.services.events.EventService;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private AgendaActivityRepository agendaActivityRepository;

    @Mock
    private EventTypeRepository eventTypeRepository;

    private UUID eventTypeId;
    private CreateEventDTO eventDTO;

    @BeforeEach
    void setUp() {
        eventTypeId = UUID.randomUUID();

        List<CreateAgendaActivityDTO> agenda = List.of(
                new CreateAgendaActivityDTO("Talk", "Opening remarks", "Main Hall",
                        LocalDateTime.of(2025, 10, 10, 10, 0),
                        LocalDateTime.of(2025, 10, 10, 11, 0))
        );

        CreateLocationDTO locationDTO = new CreateLocationDTO();
        locationDTO.setCity("Novi Sad");
        locationDTO.setAddress("Bulevar Oslobodjenja 123");

        eventDTO = new CreateEventDTO(
                "Tech Conference",
                300,
                "Technology innovation",
                EventPrivacyType.PUBLIC,
                LocalDateTime.of(2025, 10, 10, 9, 0),
                "tech.jpg",
                eventTypeId,
                agenda,
                locationDTO
        );

        // Set up a default EVENT_ORGANIZER user
        Account account = new Account();
        account.setType(PersonType.EVENT_ORGANIZER);

        Person person = new Person();
        person.setId(UUID.randomUUID());
        account.setPerson(person);

        Authentication auth = new TestingAuthenticationToken(account, null);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Should return null when user is not an event organizer")
    void shouldReturnNullWhenNotEventOrganizer() {
        Account account = new Account();
        account.setType(PersonType.AUTHENTICATED_USER);

        Authentication auth = new TestingAuthenticationToken(account, null);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        Event result = eventService.create(eventDTO);

        assertNull(result);
        verifyNoInteractions(locationRepository, agendaActivityRepository, eventRepository, eventTypeRepository);
    }

    @Test
    @DisplayName("Create event with selected event type")
    void testCreateEvent_WithEventType() {
        EventType mockEventType = new EventType();
        mockEventType.setId(eventTypeId);
        when(eventTypeRepository.findById(eventTypeId)).thenReturn(Optional.of(mockEventType));

        // when you call the repo, just return the object i passed you
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Event createdEvent = eventService.create(eventDTO);

        assertNotNull(createdEvent);
        assertEquals("Tech Conference", createdEvent.getName());
        assertEquals("Technology innovation", createdEvent.getDescription());
        assertEquals(mockEventType, createdEvent.getEventType());
        assertEquals(1, createdEvent.getAgendaActivities().size());

        verify(locationRepository).save(any(Location.class));
        verify(agendaActivityRepository).save(any(AgendaActivity.class));
        verify(eventRepository).save(any(Event.class));
    }


    @Test
    @DisplayName("Create event without specifying event type, selecting all")
    void testCreateEvent_WithNullEventType() {
        eventDTO.setEventTypeId(null);
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Event createdEvent = eventService.create(eventDTO);

        assertNotNull(createdEvent);
        assertNull(createdEvent.getEventType());

        verify(eventTypeRepository, never()).findById(any());
        verify(locationRepository).save(any(Location.class));
        verify(agendaActivityRepository).save(any(AgendaActivity.class));
        verify(eventRepository).save(any(Event.class));
    }

    @Test
    @DisplayName("Create event with multiple agenda activities")
    void testCreateEvent_MultipleAgendaActivities() {
        List<CreateAgendaActivityDTO> agenda = List.of(
                new CreateAgendaActivityDTO("Opening", "Start", "Main",
                        LocalDateTime.of(2025, 10, 10, 10, 0),
                        LocalDateTime.of(2025, 10, 10, 11, 0)),
                new CreateAgendaActivityDTO("Lunch", "Break", "Cafeteria",
                        LocalDateTime.of(2025, 10, 10, 11, 0),
                        LocalDateTime.of(2025, 10, 10, 12, 0))
        );
        eventDTO.setAgendaActivities(agenda);
        when(eventRepository.save(any(Event.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Event createdEvent = eventService.create(eventDTO);

        assertNotNull(createdEvent);
        assertEquals(2, createdEvent.getAgendaActivities().size());
        verify(agendaActivityRepository, times(2)).save(any(AgendaActivity.class));
    }

    @Test
    @DisplayName("Should return null when agenda is empty")
    void shouldReturnNull_whenNoAgenda() {
        eventDTO.setAgendaActivities(new ArrayList<>());

        Event createdEvent = eventService.create(eventDTO);

        assertNull(createdEvent);

        verify(locationRepository, times(0)).save(any(Location.class));
        verify(agendaActivityRepository, times(0)).save(any(AgendaActivity.class));
        verify(eventRepository, times(0)).save(any(Event.class));
    }

    @Test
    @DisplayName("Should return null when event name is empty")
    void shouldReturnNull_whenEventNameIsEmpty() {
        EventType mockEventType = new EventType();
        mockEventType.setId(eventTypeId);

        eventDTO.setName(null);
        Event createdEvent = eventService.create(eventDTO);

        assertNull(createdEvent);

        verify(locationRepository, times(0)).save(any(Location.class));
        verify(agendaActivityRepository, times(0)).save(any(AgendaActivity.class));
        verify(eventRepository, times(0)).save(any(Event.class));
    }

    @Test
    @DisplayName("Should return null when event description is empty")
    void shouldReturnNull_whenDescriptionIsEmpty() {
        EventType mockEventType = new EventType();
        mockEventType.setId(eventTypeId);

        eventDTO.setDescription(null);
        Event createdEvent = eventService.create(eventDTO);

        assertNull(createdEvent);

        verify(locationRepository, times(0)).save(any(Location.class));
        verify(agendaActivityRepository, times(0)).save(any(AgendaActivity.class));
        verify(eventRepository, times(0)).save(any(Event.class));
    }

    @Test
    @DisplayName("Should return null when theres a negative maximum number of participants")
    void shouldReturnNull_whenNegativeNumberOfParticipants() {
        EventType mockEventType = new EventType();
        mockEventType.setId(eventTypeId);

        eventDTO.setMaxAttendance(-1);
        Event createdEvent = eventService.create(eventDTO);

        assertNull(createdEvent);

        verify(locationRepository, times(0)).save(any(Location.class));
        verify(agendaActivityRepository, times(0)).save(any(AgendaActivity.class));
        verify(eventRepository, times(0)).save(any(Event.class));
    }

    @Test
    @DisplayName("Should return null when event time is empty")
    void shouldReturnNull_whenTimeIsEmpty() {
        EventType mockEventType = new EventType();
        mockEventType.setId(eventTypeId);

        eventDTO.setTime(null);
        Event createdEvent = eventService.create(eventDTO);

        assertNull(createdEvent);

        verify(locationRepository, times(0)).save(any(Location.class));
        verify(agendaActivityRepository, times(0)).save(any(AgendaActivity.class));
        verify(eventRepository, times(0)).save(any(Event.class));
    }

    @Test
    @DisplayName("Should return null when no event privacy type")
    void shouldReturnNull_whenNoPrivacyType() {
        EventType mockEventType = new EventType();
        mockEventType.setId(eventTypeId);

        eventDTO.setEventPrivacyType(null);
        Event createdEvent = eventService.create(eventDTO);

        assertNull(createdEvent);

        verify(locationRepository, times(0)).save(any(Location.class));
        verify(agendaActivityRepository, times(0)).save(any(AgendaActivity.class));
        verify(eventRepository, times(0)).save(any(Event.class));
    }

}
