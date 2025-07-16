package com.ftn.event_hopper.controller;

import com.ftn.event_hopper.dtos.budgets.BudgetManagementDTO;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.util.TokenUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BudgetsControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TokenUtils tokenUtils;

    private UUID eventId = UUID.fromString("6915ce46-d213-424b-a3c4-035767714df0");


    @Test
    void shouldReturnBudgetManagementSuccessfully() {
        Account account = new Account();
        account.setId(UUID.fromString("49a1dae3-323c-460b-bbcd-0fc1132e6bb1"));
        account.setActive(true);
        account.setVerified(true);
        account.setType(PersonType.EVENT_ORGANIZER); // Assuming type `2` corresponds to `EVENT_ORGANIZER`
        account.setEmail("organzier2@example.com");


        String token = tokenUtils.generateToken(account);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Authorization: Bearer <token>

        HttpEntity<?> request = new HttpEntity<>(headers);


        // When
        ResponseEntity<BudgetManagementDTO> response = restTemplate.exchange(
                "/api/budgets/" + eventId + "/management",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}
        );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(eventId, response.getBody().getEvent().getId());
    }

    @Test
    void shouldReturnErrorForInvalidEventId() {
        Account account = new Account();
        account.setId(UUID.fromString("49a1dae3-323c-460b-bbcd-0fc1132e6bb1"));
        account.setActive(true);
        account.setVerified(true);
        account.setType(PersonType.EVENT_ORGANIZER); // Assuming type `2` corresponds to `EVENT_ORGANIZER`
        account.setEmail("organzier2@example.com");


        String token = tokenUtils.generateToken(account);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Authorization: Bearer <token>

        HttpEntity<?> request = new HttpEntity<>(headers);



        // Given
        UUID invalidId = UUID.randomUUID();

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                "/api/budgets/" + invalidId + "/management",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("message"));
    }


}
