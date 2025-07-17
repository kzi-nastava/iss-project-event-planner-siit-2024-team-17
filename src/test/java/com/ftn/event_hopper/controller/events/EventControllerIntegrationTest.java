package com.ftn.event_hopper.controller.events;

import com.ftn.event_hopper.dtos.events.CreateAgendaActivityDTO;
import com.ftn.event_hopper.dtos.events.CreateEventDTO;
import com.ftn.event_hopper.dtos.location.CreateLocationDTO;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.PersonType;
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

import java.time.LocalDateTime;
import java.util.List;
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


    @Test
    void shouldCreateEventSuccessfully() {
        Account account = new Account();
        account.setId(UUID.fromString("49a1dae3-323c-460b-bbcd-0fc1132e6bb1"));
        account.setActive(true);
        account.setVerified(true);
        account.setType(PersonType.EVENT_ORGANIZER);
        account.setEmail("organzier2@example.com");

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

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
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
    void shouldReturnBadRequestWhenEventNameIsMissing() {
        Account account = new Account();
        account.setId(UUID.fromString("49a1dae3-323c-460b-bbcd-0fc1132e6bb1"));
        account.setActive(true);
        account.setVerified(true);
        account.setType(PersonType.EVENT_ORGANIZER);
        account.setEmail("organzier2@example.com");

        String token = tokenUtils.generateToken(account);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);


        eventDTO.setName(null);
        HttpEntity<CreateEventDTO> request = new HttpEntity<>(eventDTO, headers);

        ResponseEntity<CreateEventDTO> response = restTemplate.exchange(
                "/api/events",
                HttpMethod.POST,
                request,
                CreateEventDTO.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

}
