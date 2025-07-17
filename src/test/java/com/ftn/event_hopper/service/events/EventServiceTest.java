package com.ftn.event_hopper.service.events;

import com.ftn.event_hopper.dtos.events.CreateAgendaActivityDTO;
import com.ftn.event_hopper.dtos.events.CreateEventDTO;
import com.ftn.event_hopper.dtos.location.CreateLocationDTO;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.events.AgendaActivity;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.locations.Location;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import com.ftn.event_hopper.repositories.eventTypes.EventTypeRepository;
import com.ftn.event_hopper.repositories.events.AgendaActivityRepository;
import com.ftn.event_hopper.repositories.events.EventRepository;

import com.ftn.event_hopper.repositories.locations.LocationRepository;
import com.ftn.event_hopper.services.events.EventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

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
                new CreateAgendaActivityDTO("Talk", "Opening remarks","Main Hall", LocalDateTime.of(2025, 10, 10, 10, 0), LocalDateTime.of(2025, 10, 10, 11, 0))
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
    }

    @Test
    @DisplayName("Create Valid event")
    void testCreateEvent_WithEventType() {
        EventType mockEventType = new EventType();
        mockEventType.setId(eventTypeId);
        when(eventTypeRepository.findById(eventTypeId)).thenReturn(Optional.of(mockEventType));

        Event savedEvent = new Event();
        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        Event createdEvent = eventService.create(eventDTO);

        assertNotNull(createdEvent);
        verify(locationRepository, times(1)).save(any(Location.class));
        verify(agendaActivityRepository, times(1)).save(any(AgendaActivity.class));
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    @DisplayName("Create Event when all is selected under event type")
    void testCreateEvent_WithNullEventType() {
        eventDTO.setEventTypeId(null); // simulate "all" selection

        Event savedEvent = new Event();
        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        Event createdEvent = eventService.create(eventDTO);

        assertNotNull(createdEvent);
        assertNull(createdEvent.getEventType());

        verify(eventTypeRepository, never()).findById(any());
        verify(locationRepository, times(1)).save(any(Location.class));
        verify(agendaActivityRepository, times(1)).save(any(AgendaActivity.class));
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    @DisplayName("Create Multiple Agenda activities")
    void testCreateEvent_MultipleAgendaActivities() {
        List<CreateAgendaActivityDTO> agenda = List.of(
                new CreateAgendaActivityDTO("Opening", "Start", "Main", LocalDateTime.of(2025, 10, 10, 10, 0), LocalDateTime.of(2025, 10, 10, 11, 0)),
                new CreateAgendaActivityDTO("Lunch", "Break", "Cafeteria", LocalDateTime.of(2025, 10, 10, 11, 0), LocalDateTime.of(2025, 10, 10, 12, 0))
        );
        eventDTO.setAgendaActivities(agenda);

        when(eventRepository.save(any(Event.class))).thenReturn(new Event());

        Event createdEvent = eventService.create(eventDTO);

        assertNotNull(createdEvent);
        verify(agendaActivityRepository, times(2)).save(any(AgendaActivity.class));
    }


    @Test
    @DisplayName("Create Event despite no agenda activities")
    void testCreateEvent_NoAgendaActivities() {
        List<CreateAgendaActivityDTO> agenda = new ArrayList<>();
        eventDTO.setAgendaActivities(agenda);

        when(eventRepository.save(any(Event.class))).thenReturn(new Event());

        Event createdEvent = eventService.create(eventDTO);

        assertNotNull(createdEvent);
        verify(eventRepository, times(1)).save(any(Event.class));
    }
}

