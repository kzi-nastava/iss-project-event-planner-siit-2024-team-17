package com.ftn.event_hopper.controller;

import com.ftn.event_hopper.dtos.budgets.BudgetManagementDTO;
import com.ftn.event_hopper.dtos.budgets.UpdateBudgetItemDTO;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.util.TokenUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class BudgetsControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TokenUtils tokenUtils;

    private final UUID eventId = UUID.fromString("6915ce46-d213-424b-a3c4-035767714df0");

    private HttpHeaders getHeadersWithCorrectToken() {
        Account account = new Account();
        account.setId(UUID.fromString("49a1dae3-323c-460b-bbcd-0fc1132e6bb1"));
        account.setActive(true);
        account.setVerified(true);
        account.setType(PersonType.EVENT_ORGANIZER);
        account.setEmail("organzier2@example.com");

        String token = tokenUtils.generateToken(account);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Authorization: Bearer <token>
        return headers;
    }


    @Test
    @DisplayName("Should return budget management successfully")
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
    @DisplayName("Should return error for invalid event ID")
    void shouldReturnErrorForInvalidEventId() {
        HttpEntity<?> request = new HttpEntity<>(getHeadersWithCorrectToken());

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

    @Test
    @DisplayName("Should return error for unauthorized access")
    void shouldAddBudgetItemsSuccessfully() {
        UpdateBudgetItemDTO item1 = new UpdateBudgetItemDTO();
        item1.setId(null);
        item1.setCategoryId(UUID.fromString("d4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d"));
        item1.setAmount(500.0);

        UpdateBudgetItemDTO item2 = new UpdateBudgetItemDTO();
        item2.setId(null);
        item2.setCategoryId(UUID.fromString("a7c5e2b9-d3f4-49b8-b6c1-3f9e7a4d5b2c"));
        item2.setAmount(300.0);

        Collection<UpdateBudgetItemDTO> addItems = List.of(item1, item2);

        HttpEntity<Collection<UpdateBudgetItemDTO>> request = new HttpEntity<>(addItems, getHeadersWithCorrectToken());

        ResponseEntity<BudgetManagementDTO> response = restTemplate.exchange(
                "/api/budgets/" + eventId,
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(eventId, response.getBody().getEvent().getId());
    }

    @Test
    @DisplayName("Should return error for unauthorized access")
    void shouldReturnErrorWhenWrongEventIdFails() {
        HttpEntity<Collection<UpdateBudgetItemDTO>> request = new HttpEntity<>(List.of(), getHeadersWithCorrectToken());

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                "/api/budgets/" + UUID.randomUUID(),
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("message"));
    }



}
