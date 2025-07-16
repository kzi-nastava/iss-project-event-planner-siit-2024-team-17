package com.ftn.event_hopper.service.budgets;

import com.ftn.event_hopper.dtos.budgets.BudgetManagementDTO;
import com.ftn.event_hopper.dtos.budgets.UpdateBudgetItemDTO;
import com.ftn.event_hopper.dtos.categories.SimpleCategoryDTO;
import com.ftn.event_hopper.dtos.eventTypes.SimpleEventTypeDTO;
import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.mapper.categories.CategoryDTOMapper;
import com.ftn.event_hopper.mapper.events.EventDTOMapper;
import com.ftn.event_hopper.mapper.solutions.ProductDTOMapper;
import com.ftn.event_hopper.models.budgets.BudgetItem;
import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.prices.Price;
import com.ftn.event_hopper.models.reservations.Reservation;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.EventOrganizer;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.repositories.categoies.CategoryRepository;
import com.ftn.event_hopper.repositories.events.EventRepository;
import com.ftn.event_hopper.repositories.reservations.ReservationRepository;
import com.ftn.event_hopper.repositories.users.EventOrganizerRepository;
import com.ftn.event_hopper.services.budgets.BudgetService;
import com.ftn.event_hopper.services.categories.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BudgetServiceTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventOrganizerRepository eventOrganizerRepository;
    @Mock
    private CategoryDTOMapper categoryDTOMapper;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ProductDTOMapper productDTOMapper;
    @Mock
    private EventDTOMapper eventDTOMapper;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryService categoryService;
    @InjectMocks
    private BudgetService budgetService;

    @BeforeEach
    void setUp() {
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
    void shouldReturnBudgetManagementDTOWhenValidEventAndAuthorized() {
        UUID eventId = UUID.randomUUID();

        Event event = new Event();
        event.setId(eventId);
        BudgetItem budgetItem = new BudgetItem();
        budgetItem.setId(UUID.randomUUID());
        budgetItem.setAmount(100.0);
        Category category = new Category();
        budgetItem.setCategory(category);
        event.setBudgetItems(Set.of(budgetItem));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));
        when(eventOrganizerRepository.findById(any())).thenReturn(Optional.of(organizer));

        Reservation reservation = new Reservation();
        reservation.setTimestamp(LocalDateTime.now());

        Product product = Mockito.mock(Product.class);
        Price price = new Price();
        price.setFinalPrice(50.0);
        when(product.getPriceAtTimestamp(any())).thenReturn(price);
        reservation.setProduct(product);
        when(reservationRepository.findByEvent(event)).thenReturn(List.of(reservation));

        when(categoryDTOMapper.fromCategoryToSimpleCategoryDTO(category)).thenReturn(new SimpleCategoryDTO());
        when(productDTOMapper.fromProductListToSimpleDTOList(Mockito.anyList())).thenReturn(List.of(new SimpleProductDTO()));
        SimpleEventDTO eventSimpleDTO = new SimpleEventDTO();
        SimpleEventTypeDTO typeDTO = new SimpleEventTypeDTO();
        typeDTO.setId(UUID.randomUUID());
        eventSimpleDTO.setEventType(typeDTO);
        when(eventDTOMapper.fromEventToSimpleDTO(event)).thenReturn(eventSimpleDTO);
        when(categoryService.getCategoriesForEventType(typeDTO.getId())).thenReturn(List.of(new Category()));
        when(categoryDTOMapper.fromCategoryListToSimpleDTOList(Mockito.anyList())).thenReturn(List.of(new SimpleCategoryDTO()));

        when(product.getCurrentPrice()).thenReturn(price);

        BudgetManagementDTO dto = budgetService.findById(eventId);

        assertNotNull(dto);
        assertEquals(1, dto.getBudgetItems().size());
        assertEquals(50.0, dto.getLeftAmount()); // 100 - 50 spent
    }

    @Test
    void shouldThrowExceptionWhenEventNotFound() {
        UUID eventId = UUID.randomUUID();
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> budgetService.findById(eventId));
    }

    @Test
    void shouldThrowWhenNotEventOrganizer() {
        Account account = new Account();
        account.setType(PersonType.AUTHENTICATED_USER); // Not an event organizer

        Authentication auth = new TestingAuthenticationToken(account, null);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);

        SecurityContextHolder.setContext(context);

        UUID eventId = UUID.randomUUID();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(new Event()));

        assertThrows(RuntimeException.class, () -> budgetService.findById(eventId));
    }

    @Test
    void shouldUpdateBudgetItemsSuccessfully() {
        UUID eventId = UUID.randomUUID();
        UUID budgetItemId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Event event = new Event();
        event.setId(eventId);

        Category category = new Category();
        category.setId(categoryId);

        BudgetItem existingBudgetItem = new BudgetItem();
        existingBudgetItem.setId(budgetItemId);
        existingBudgetItem.setAmount(100.0);
        existingBudgetItem.setCategory(category);

        event.setBudgetItems(new HashSet<>(List.of(existingBudgetItem)));

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));

        UpdateBudgetItemDTO updatedItem = new UpdateBudgetItemDTO();
        updatedItem.setId(budgetItemId);
        updatedItem.setAmount(200.0);

        Product product = Mockito.mock(Product.class);
        Price price = new Price();
        price.setFinalPrice(0.0);
        product.setPrices(List.of(price));

        Reservation reservation = new Reservation();
        reservation.setProduct(product);

        SimpleEventTypeDTO eventTypeDTO = new SimpleEventTypeDTO();
        eventTypeDTO.setId(UUID.randomUUID());

        SimpleEventDTO eventSimpleDTO = new SimpleEventDTO();
        eventSimpleDTO.setEventType(eventTypeDTO);

        // Mocks
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventOrganizerRepository.findById(any())).thenReturn(Optional.of(organizer));
        when(reservationRepository.findByEvent(event)).thenReturn(List.of(reservation));
        when(product.getCurrentPrice()).thenReturn(price);
        when(product.getPriceAtTimestamp(any())).thenReturn(price);
        when(eventDTOMapper.fromEventToSimpleDTO(event)).thenReturn(eventSimpleDTO);
        when(categoryService.getCategoriesForEventType(eventTypeDTO.getId()))
                .thenReturn(List.of(new Category()));
        when(categoryDTOMapper.fromCategoryListToSimpleDTOList(anyList()))
                .thenReturn(List.of(new SimpleCategoryDTO()));

        BudgetManagementDTO result = budgetService.update(eventId, List.of(updatedItem));

        assertNotNull(result);
        assertEquals(200.0, event.getBudgetItems().iterator().next().getAmount());

        verify(eventRepository, times(1)).save(event);
        verify(eventRepository, times(1)).flush();
    }

    @Test
    void shouldThrowIfAmountIsNegative() {
        UUID eventId = UUID.randomUUID();
        UpdateBudgetItemDTO dto = new UpdateBudgetItemDTO();
        dto.setAmount(-5.0);
        dto.setId(UUID.randomUUID());

        Event event = new Event();
        event.setId(eventId);
        event.setBudgetItems(new HashSet<>());

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventOrganizerRepository.findById(any())).thenReturn(Optional.of(organizer));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            budgetService.update(eventId, List.of(dto));
        });

        assertEquals("Amount cannot be negative", exception.getMessage());
    }
}
