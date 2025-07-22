package com.ftn.event_hopper.controller.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftn.event_hopper.dtos.events.CreateAgendaActivityDTO;
import com.ftn.event_hopper.dtos.events.CreateEventDTO;
import com.ftn.event_hopper.dtos.location.CreateLocationDTO;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EventControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String getAuthTokenForEventOrganizer() {
        String json = """
                {
                  "email": "organizer3@example.com",
                  "password": "Password123"
                }
                """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(json, headers);

        ResponseEntity<String> response = restTemplate.postForEntity("/api/login", request, String.class);

        // Your backend should return the JWT token in Authorization header or body, adapt if needed
        return response.getHeaders().getFirst("Authorization"); // Bearer ...
    }

    @Test
    @DisplayName("Should create event when authenticated as EVENT_ORGANIZER")
    public void shouldCreateEventAsOrganizer() {
        CreateLocationDTO location = new CreateLocationDTO();
        location.setAddress("Bulevar Oslobodjenja 123");
        location.setCity("Novi Sad");
        CreateAgendaActivityDTO activity = new CreateAgendaActivityDTO(
                "Keynote",
                "Opening talk",
                "Main Hall",
                LocalDateTime.of(2025, 10, 10, 9, 0),
                LocalDateTime.of(2025, 10, 10, 10, 0)
        );

        CreateEventDTO eventDTO = new CreateEventDTO(
                "Tech Conference",
                300,
                "Technology innovation",
                EventPrivacyType.PUBLIC,
                LocalDateTime.of(2025, 10, 10, 9, 0),
                "tech.jpg",
                null, // if your test DB has one, insert UUID here
                List.of(activity),
                location
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(getAuthTokenForEventOrganizer());

        HttpEntity<CreateEventDTO> request = new HttpEntity<>(eventDTO, headers);

        // Act
        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/events",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    @DisplayName("Should return 404 if not authenticated as organizer")
    public void shouldReturn404IfNotOrganizer() {
        // Arrange
        CreateLocationDTO location = new CreateLocationDTO();
        location.setAddress("Bulevar Oslobodjenja 123");
        location.setCity("Novi Sad");
        CreateEventDTO eventDTO = new CreateEventDTO(
                "Unauthorized Event",
                100,
                "Should fail",
                EventPrivacyType.PRIVATE,
                LocalDateTime.of(2025, 10, 10, 9, 0),
                "fail.jpg",
                null,
                Collections.emptyList(),
                location
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // No auth header

        HttpEntity<CreateEventDTO> request = new HttpEntity<>(eventDTO, headers);

        // Act
        ResponseEntity<Void> response = restTemplate.exchange(
                "/api/events",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {}
        );

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
