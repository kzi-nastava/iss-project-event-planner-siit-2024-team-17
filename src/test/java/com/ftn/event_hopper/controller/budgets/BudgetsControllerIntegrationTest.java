package com.ftn.event_hopper.controller.budgets;

import com.ftn.event_hopper.dtos.budgets.BudgetItemManagementDTO;
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

import java.util.Collection;
import java.util.List;
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

    private final UUID eventId = UUID.fromString("2d4a7c9e-6f3b-42a1-b8f5-3c7e9b6a4d5f");

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

    private HttpHeaders getHeadersWithBadToken() {
        Account account = new Account();
        account.setId(UUID.fromString("d7e7937b-02f3-47d7-bf64-f6e1555ff828"));
        account.setActive(true);
        account.setVerified(true);
        account.setType(PersonType.EVENT_ORGANIZER);
        account.setEmail("user2@example.com");

        String token = tokenUtils.generateToken(account);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token); // Authorization: Bearer <token>
        return headers;
    }


    @Test
    @DisplayName("GET /api/budgets/:eventId/management - Should return budget management successfully")
    void shouldReturnBudgetManagementSuccessfully() {
        HttpHeaders headers = getHeadersWithCorrectToken();

        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<BudgetManagementDTO> response = restTemplate.exchange(
                "/api/budgets/" + eventId + "/management",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(eventId, response.getBody().getEvent().getId());
        assertNotNull(response.getBody().getBudgetItems());
        assertFalse(response.getBody().getBudgetItems().isEmpty());
        assertTrue(response.getBody().getBudgetItems().stream()
                .anyMatch(item -> item.getCategory().getId()
                        .equals(UUID.fromString("d4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d"))));
    }

    @Test
    @DisplayName("GET /api/budgets/:eventId/management - Should return error for invalid event ID")
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
    @DisplayName("PUT /api/budgets/:eventId - Should add budget items successfully")
    void shouldAddBudgetItemsSuccessfully() {
        UpdateBudgetItemDTO item1 = new UpdateBudgetItemDTO();
        item1.setId(UUID.fromString("a5f8e6b7-d2d5-4376-8a9b-7c4f3b3c1e1e"));
        item1.setCategoryId(UUID.fromString("d4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d"));
        item1.setAmount(6000);

        UpdateBudgetItemDTO item2 = new UpdateBudgetItemDTO();
        item2.setId(null);
        item2.setCategoryId(UUID.fromString("b1d5d5c5-f6c1-4039-a8a5-4fc3ea0a4e2a"));
        item2.setAmount(500);

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
        assertNotNull(response.getBody().getBudgetItems().stream()
                .filter(item -> item.getCategory().getId()
                        .equals(UUID.fromString("b1d5d5c5-f6c1-4039-a8a5-4fc3ea0a4e2a")))
                .findFirst().orElse(null));
    }

    @Test
    @DisplayName("PUT /api/budgets/:eventId - Should return error for invalid event")
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

    @Test
    @DisplayName("PUT /api/budgets/:eventId - Should update budget items successfully")
    void shouldUpdateBudgetItemsSuccessfully() {
        UpdateBudgetItemDTO item1 = new UpdateBudgetItemDTO();
        item1.setId(UUID.fromString("a5f8e6b7-d2d5-4376-8a9b-7c4f3b3c1e1e"));
        item1.setCategoryId(UUID.fromString("d4f4e6b7-d2d5-4376-8a9b-7c4f3b3c1e7d"));
        item1.setAmount(300);

        Collection<UpdateBudgetItemDTO> updateItems = List.of(item1);

        HttpEntity<Collection<UpdateBudgetItemDTO>> request = new HttpEntity<>(updateItems, getHeadersWithCorrectToken());

        ResponseEntity<BudgetManagementDTO> response = restTemplate.exchange(
                "/api/budgets/" + eventId,
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(eventId, response.getBody().getEvent().getId());
        BudgetItemManagementDTO retItem = response.getBody().getBudgetItems().stream()
                .filter(item -> item.getId()
                        .equals(UUID.fromString("a5f8e6b7-d2d5-4376-8a9b-7c4f3b3c1e1e")))
                .findFirst().orElse(null);
        assertNotNull(retItem);
        assertEquals(300, retItem.getAmount());
    }

    @Test
    @DisplayName("GET /api/budgets/:eventId/management - Should return error for unauthorized access")
    void shouldReturnErrorForUnauthorizedAccessGet() {
        HttpEntity<Collection<UpdateBudgetItemDTO>> request = new HttpEntity<>(List.of(), getHeadersWithBadToken());

        ResponseEntity<Map<String, String>> response = restTemplate.exchange(
                "/api/budgets/" + eventId + "/management",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().containsKey("error"));
    }


    @Test
    @DisplayName("PUT /api/budgets/:eventId - Should return error for unauthorized access")
    void shouldReturnErrorForUnauthorizedAccessPut() {
        HttpEntity<Collection<UpdateBudgetItemDTO>> request = new HttpEntity<>(List.of(), getHeadersWithBadToken());

        ResponseEntity<BudgetManagementDTO> response = restTemplate.exchange(
                "/api/budgets/" + eventId,
                HttpMethod.PUT,
                request,
                new ParameterizedTypeReference<>() {}
        );

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}
