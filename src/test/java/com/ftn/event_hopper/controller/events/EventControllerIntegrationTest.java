package com.ftn.event_hopper.controller.events;

import com.ftn.event_hopper.dtos.events.CreateAgendaActivityDTO;
import com.ftn.event_hopper.dtos.events.CreateEventDTO;
import com.ftn.event_hopper.dtos.events.CreatedEventDTO;
import com.ftn.event_hopper.dtos.location.CreateLocationDTO;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.repositories.events.AgendaActivityRepository;
import com.ftn.event_hopper.repositories.events.EventRepository;
import com.ftn.event_hopper.util.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EventControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TokenUtils tokenUtils;

    private CreateEventDTO eventDTO;

    @Autowired
    private EventRepository eventRepository;


    private HttpHeaders getHeadersWithCorrectToken() {
        Account account = new Account();
        account.setId(UUID.fromString("035ad44b-2ca3-4775-be2d-c65e6c1eb084"));
        account.setActive(true);
        account.setVerified(true);
        account.setType(PersonType.EVENT_ORGANIZER);
        account.setEmail("organizer3@example.com");

        String token = tokenUtils.generateToken(account);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Authorization: Bearer <token>
        return headers;
    }

    @BeforeEach
    void setUp() {
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
                UUID.randomUUID(),
                agenda,
                locationDTO
        );

    }

    @Test
    void shouldReturnNotFoundWhenUserIsNotEventOrganizer() {
        Account account = new Account();
        account.setId(UUID.fromString("31548d6b-019f-492e-ba39-07be7a1433e5"));
        account.setActive(true);
        account.setVerified(true);
        account.setType(PersonType.SERVICE_PROVIDER);
        account.setEmail("pup3@example.com");


        String token = tokenUtils.generateToken(account);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateEventDTO> request = new HttpEntity<>(eventDTO, headers);

        ResponseEntity<CreateEventDTO> response = restTemplate.exchange(
                "/api/events",
                HttpMethod.POST,
                request,
                CreateEventDTO.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @Transactional
    void shouldCreateEventSuccessfully() {
        HttpHeaders headers = getHeadersWithCorrectToken();
        HttpEntity<?> request = new HttpEntity<>(eventDTO, headers);

        ResponseEntity<CreatedEventDTO> response = restTemplate.exchange(
                "/api/events",
                HttpMethod.POST,
                request,
                CreatedEventDTO.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        CreatedEventDTO createdEventDTO = response.getBody();
        Optional<Event> savedEvent = eventRepository.findById(createdEventDTO.getId());
        if(savedEvent.isPresent()){
            assertEquals(eventDTO.getName(), savedEvent.get().getName());
            assertEquals(eventDTO.getDescription(), savedEvent.get().getDescription());
            assertEquals(eventDTO.getAgendaActivities().size(), savedEvent.get().getAgendaActivities().size());
        } else {
            fail("Event was not saved in the database");
        }
    }


    //for selecting all
    @Test
    void shouldCreateEventDespiteEventTypeNull() {
        HttpHeaders headers = getHeadersWithCorrectToken();
        eventDTO.setEventTypeId(null); // Random ID not in DB
        HttpEntity<?> request = new HttpEntity<>(eventDTO, headers);

        ResponseEntity<CreatedEventDTO> response = restTemplate.exchange(
                "/api/events",
                HttpMethod.POST,
                request,
                CreatedEventDTO.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        CreatedEventDTO createdEventDTO = response.getBody();
        assertNotNull(createdEventDTO);

        Optional<Event> savedEvent = eventRepository.findById(createdEventDTO.getId());
        assertTrue(savedEvent.isPresent());
        assertNull(savedEvent.get().getEventType());
        assertEquals(eventDTO.getName(), savedEvent.get().getName());
    }

    @Test
    void shouldReturnBadRequestWhenEventNameIsMissing() {
        HttpHeaders headers = getHeadersWithCorrectToken();
        eventDTO.setName(null);
        HttpEntity<?> request = new HttpEntity<>(eventDTO, headers);


        ResponseEntity<CreateEventDTO> response = restTemplate.exchange(
                "/api/events",
                HttpMethod.POST,
                request,
                CreateEventDTO.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void shouldReturnBadRequestWhenMaxAttendanceIsZero() {
        HttpHeaders headers = getHeadersWithCorrectToken();
        eventDTO.setMaxAttendance(0);
        HttpEntity<?> request = new HttpEntity<>(eventDTO, headers);

        ResponseEntity<CreateEventDTO> response = restTemplate.exchange(
                "/api/events",
                HttpMethod.POST,
                request,
                CreateEventDTO.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenAgendaActivitiesEmpty() {
        HttpHeaders headers = getHeadersWithCorrectToken();
        eventDTO.setAgendaActivities(List.of()); // empty agenda
        HttpEntity<?> request = new HttpEntity<>(eventDTO, headers);

        ResponseEntity<CreateEventDTO> response = restTemplate.exchange(
                "/api/events",
                HttpMethod.POST,
                request,
                CreateEventDTO.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenTimeIsNull() {
        HttpHeaders headers = getHeadersWithCorrectToken();
        eventDTO.setTime(null);
        HttpEntity<?> request = new HttpEntity<>(eventDTO, headers);

        ResponseEntity<CreateEventDTO> response = restTemplate.exchange(
                "/api/events",
                HttpMethod.POST,
                request,
                CreateEventDTO.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenPrivacyTypeIsNull() {
        HttpHeaders headers = getHeadersWithCorrectToken();
        eventDTO.setEventPrivacyType(null);
        HttpEntity<?> request = new HttpEntity<>(eventDTO, headers);

        ResponseEntity<CreateEventDTO> response = restTemplate.exchange(
                "/api/events",
                HttpMethod.POST,
                request,
                CreateEventDTO.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenDescriptionIsNull() {
        HttpHeaders headers = getHeadersWithCorrectToken();
        eventDTO.setDescription(null);
        HttpEntity<?> request = new HttpEntity<>(eventDTO, headers);

        ResponseEntity<CreateEventDTO> response = restTemplate.exchange(
                "/api/events",
                HttpMethod.POST,
                request,
                CreateEventDTO.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Transactional
    void shouldPersistAgendaActivitiesCorrectly() {
        HttpHeaders headers = getHeadersWithCorrectToken();
        HttpEntity<?> request = new HttpEntity<>(eventDTO, headers);

        ResponseEntity<CreatedEventDTO> response = restTemplate.exchange(
                "/api/events",
                HttpMethod.POST,
                request,
                CreatedEventDTO.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        CreatedEventDTO createdEventDTO = response.getBody();
        assertNotNull(createdEventDTO);

        Event savedEvent = eventRepository.findById(createdEventDTO.getId()).orElseThrow();

        assertEquals(eventDTO.getAgendaActivities().size(), savedEvent.getAgendaActivities().size());

        // Compare each agenda activity details
        eventDTO.getAgendaActivities().forEach(expectedActivity -> {
            boolean match = savedEvent.getAgendaActivities().stream().anyMatch(savedActivity ->
                    savedActivity.getName().equals(expectedActivity.getName()) &&
                            savedActivity.getDescription().equals(expectedActivity.getDescription()) &&
                            savedActivity.getStartTime().equals(expectedActivity.getStartTime()) &&
                            savedActivity.getEndTime().equals(expectedActivity.getEndTime()) &&
                            savedActivity.getLocationName().equals(expectedActivity.getLocationName())
            );
            assertTrue(match, "Expected agenda activity not found in saved event: " + expectedActivity.getName());
        });
    }

    @Test
    void shouldReturnBadRequestWhenPictureIsNull() {
        HttpHeaders headers = getHeadersWithCorrectToken();
        eventDTO.setPicture(null);
        HttpEntity<?> request = new HttpEntity<>(eventDTO, headers);

        ResponseEntity<CreatedEventDTO> response = restTemplate.exchange(
                "/api/events",
                HttpMethod.POST,
                request,
                CreatedEventDTO.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }





}
