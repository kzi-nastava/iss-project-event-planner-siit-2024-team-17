package com.ftn.event_hopper.controller.reservations;

import org.springframework.core.ParameterizedTypeReference;

import com.ftn.event_hopper.dtos.reservations.CreateReservationServiceDTO;
import com.ftn.event_hopper.dtos.reservations.CreatedReservationServiceDTO;
import com.ftn.event_hopper.models.reservations.Reservation;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.repositories.reservations.ReservationRepository;
import com.ftn.event_hopper.util.TokenUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReservationControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private ReservationRepository reservationRepository;

    private HttpHeaders getHeadersWithCorrectToken() {
        Account account = new Account();
        account.setId(UUID.fromString("035ad44b-2ca3-4775-be2d-c65e6c1eb084"));
        account.setActive(true);
        account.setVerified(true);
        account.setType(PersonType.EVENT_ORGANIZER);
        account.setEmail("organizer3@example.com");

        String token = tokenUtils.generateToken(account);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return headers;
    }

    private CreateReservationServiceDTO reservationServiceDTO;

    private UUID eventId = UUID.fromString("4b3a7e9c-d8f5-49a1-b2c7-5a9d7f6e3c2b");
    private UUID serviceId = UUID.fromString("935e1b52-6180-419a-bbe8-909db6cd6cbc");
    @BeforeEach
    void setUp() {

        reservationServiceDTO = new CreateReservationServiceDTO(
                eventId,
                serviceId,
                LocalDateTime.now().plusDays(2),
                LocalDateTime.now().plusDays(2).plusMinutes(30)
        );
    }


    @Test
    void shouldReturnNotFoundWhenUserIsNotEventOrganizer(){
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

        HttpEntity<CreateReservationServiceDTO> request = new HttpEntity<>(reservationServiceDTO, headers);

        ResponseEntity<CreateReservationServiceDTO> response = restTemplate.exchange(
                "/api/reservations/services",
                HttpMethod.POST,
                request,
                CreateReservationServiceDTO.class
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());

    }


    @Test
    @Transactional
    void shouldCreateReservationSuccessfully() {
        HttpHeaders headers = getHeadersWithCorrectToken();
        HttpEntity<?> request = new HttpEntity<>(reservationServiceDTO, headers);

        ResponseEntity<CreatedReservationServiceDTO> response = restTemplate.exchange(
                "/api/reservations/services",
                HttpMethod.POST,
                request,
                CreatedReservationServiceDTO.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        CreatedReservationServiceDTO createdReservationServiceDTO = response.getBody();
        Optional<Reservation> savedReservation = reservationRepository.findById(createdReservationServiceDTO.getId());
        if(savedReservation.isPresent()){
            assertEquals(createdReservationServiceDTO.getEvent().getId(), savedReservation.get().getEvent().getId());
            assertEquals(createdReservationServiceDTO.getProduct().getId(), savedReservation.get().getProduct().getId());

            assertEquals(
                    createdReservationServiceDTO.getStartTime().truncatedTo(java.time.temporal.ChronoUnit.MILLIS),
                    savedReservation.get().getStartTime().truncatedTo(java.time.temporal.ChronoUnit.MILLIS)
            );

            assertEquals(
                    createdReservationServiceDTO.getEndTime().truncatedTo(java.time.temporal.ChronoUnit.MILLIS),
                    savedReservation.get().getEndTime().truncatedTo(java.time.temporal.ChronoUnit.MILLIS)
            );

        }else{
            fail("Reservation was not saved in the database");
        }
    }

    @Test
    void shouldReturnBadRequestWhenEventIsMissing(){
        HttpHeaders headers = getHeadersWithCorrectToken();
        reservationServiceDTO.setEventId(null);
        HttpEntity<?> request = new HttpEntity<>(reservationServiceDTO, headers);
        ResponseEntity<CreatedReservationServiceDTO> response = restTemplate.exchange(
                "/api/reservations/services",
                HttpMethod.POST,
                request,
                CreatedReservationServiceDTO.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    void shouldReturnBadRequestWhenServiceIsMissing(){

        HttpHeaders headers = getHeadersWithCorrectToken();
        reservationServiceDTO.setProductId(null);
        HttpEntity<?> request = new HttpEntity<>(reservationServiceDTO, headers);
        ResponseEntity<CreatedReservationServiceDTO> response = restTemplate.exchange(
                "/api/reservations/services",
                HttpMethod.POST,
                request,
                CreatedReservationServiceDTO.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void shouldReturnBadRequestWhenStartTimeIsMissing(){
        HttpHeaders headers = getHeadersWithCorrectToken();
        reservationServiceDTO.setFrom(null);
        HttpEntity<?> request = new HttpEntity<>(reservationServiceDTO, headers);
        ResponseEntity<CreatedReservationServiceDTO> response = restTemplate.exchange(
                "/api/reservations/services",
                HttpMethod.POST,
                request,
                CreatedReservationServiceDTO.class
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }
    @Test
    void shouldReturnBadRequestWhenEndTimeIsMissing(){
        HttpHeaders headers = getHeadersWithCorrectToken();
        reservationServiceDTO.setTo(null);
        HttpEntity<?> request = new HttpEntity<>(reservationServiceDTO, headers);
        ResponseEntity<CreatedReservationServiceDTO> response = restTemplate.exchange(
                "/api/reservations/services",
                HttpMethod.POST,
                request,
                CreatedReservationServiceDTO.class
        );

    }

//    @Test
//    void shouldReturnAvailableTermsSuccessfully() {
//
//        HttpHeaders headers = getHeadersWithCorrectToken();
//        UUID serviceId = UUID.fromString("935e1b52-6180-419a-bbe8-909db6cd6cbc");
//        String dateParam = LocalDateTime.now().plusDays(1).toString();
//
//        ResponseEntity<List<LocalDateTime>> response = restTemplate.exchange(
//                "/api/reservations/services/" + serviceId + "/terms/?date=" + dateParam,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<LocalDateTime>>() {}
//        );
//
//        List<LocalDateTime> terms = response.getBody();
//
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody(), "Response body should not be null");
//    }




}
