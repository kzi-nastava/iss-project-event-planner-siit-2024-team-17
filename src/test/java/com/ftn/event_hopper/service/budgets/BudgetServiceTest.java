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
import org.junit.jupiter.api.DisplayName;
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

import org.springframework.security.access.AccessDeniedException;
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
    @DisplayName("Should return valid BudgetManagementDTO when event is found and user is authorized")
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
        assertEquals(50.0, dto.getLeftAmount());
    }

    @Test
    @DisplayName("findById - Should throw EntityNotFoundException when event is not found")
    void shouldThrowExceptionWhenEventNotFound() {
        UUID eventId = UUID.randomUUID();
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> budgetService.findById(eventId));
    }

    @Test
    @DisplayName("findById - Should throw RuntimeException when user is not an event organizer")
    void shouldThrowWhenNotEventOrganizer() {
        Account account = new Account();
        account.setType(PersonType.AUTHENTICATED_USER);

        Authentication auth = new TestingAuthenticationToken(account, null);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);

        SecurityContextHolder.setContext(context);

        UUID eventId = UUID.randomUUID();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(new Event()));

        assertThrows(RuntimeException.class, () -> budgetService.findById(eventId));
    }

    @Test
    @DisplayName("findById - Should throw EntityNotFoundException when account is null")
    void shouldThrowWhenAccountIsNull() {
        Authentication auth = new TestingAuthenticationToken(null, null);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        UUID eventId = UUID.randomUUID();

        assertThrows(EntityNotFoundException.class, () -> budgetService.findById(eventId));
    }

    @Test
    @DisplayName("findById - Should throw EntityNotFoundException when event organizer is not found")
    void shouldThrowWhenEventOrganizerNotFound() {
        UUID eventId = UUID.randomUUID();

        Account account = new Account();
        account.setType(PersonType.EVENT_ORGANIZER);
        Person person = new Person();
        person.setId(UUID.randomUUID());
        account.setPerson(person);

        Authentication auth = new TestingAuthenticationToken(account, null);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(new Event()));
        when(eventOrganizerRepository.findById(person.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> budgetService.findById(eventId));
    }

    @Test
    @DisplayName("findById - Should throw RuntimeException when event is not owned by organizer")
    void shouldThrowWhenEventNotOwnedByOrganizer() {
        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);

        Account account = new Account();
        account.setType(PersonType.EVENT_ORGANIZER);
        Person person = new Person();
        person.setId(UUID.randomUUID());
        account.setPerson(person);

        Authentication auth = new TestingAuthenticationToken(account, null);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of());
        when(eventOrganizerRepository.findById(person.getId())).thenReturn(Optional.of(organizer));

        assertThrows(RuntimeException.class, () -> budgetService.findById(eventId));
    }

    @Test
    @DisplayName("findById - Should mark as deletable when no reservations exist")
    void shouldMarkBudgetItemsAsDeletableWhenNoReservations() {
        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);

        BudgetItem item = new BudgetItem();
        item.setId(UUID.randomUUID());
        item.setAmount(100.0);
        Category category = new Category();
        item.setCategory(category);
        event.setBudgetItems(Set.of(item));

        Account account = new Account();
        account.setType(PersonType.EVENT_ORGANIZER);
        Person person = new Person();
        person.setId(UUID.randomUUID());
        account.setPerson(person);

        Authentication auth = new TestingAuthenticationToken(account, null);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));
        when(eventOrganizerRepository.findById(person.getId())).thenReturn(Optional.of(organizer));

        SimpleEventDTO eventSimpleDTO = new SimpleEventDTO();
        SimpleEventTypeDTO typeDTO = new SimpleEventTypeDTO();
        typeDTO.setId(UUID.randomUUID());
        eventSimpleDTO.setEventType(typeDTO);

        when(eventDTOMapper.fromEventToSimpleDTO(event)).thenReturn(eventSimpleDTO);
        when(categoryService.getCategoriesForEventType(typeDTO.getId())).thenReturn(List.of());
        when(reservationRepository.findByEvent(event)).thenReturn(Collections.emptyList());

        BudgetManagementDTO dto = budgetService.findById(eventId);

        assertNotNull(dto);
        assertEquals(1, dto.getBudgetItems().size());
        assertTrue(new ArrayList<>(dto.getBudgetItems()).get(0).isDeletable());
    }

    @Test
    @DisplayName("findById - Should skip price when price at timestamp is null")
    void shouldSkipPriceWhenPriceAtTimestampIsNull() {
        UUID eventId = UUID.randomUUID();
        Event event = new Event();
        event.setId(eventId);

        BudgetItem item = new BudgetItem();
        item.setId(UUID.randomUUID());
        item.setAmount(200.0);
        Category category = new Category();
        item.setCategory(category);
        event.setBudgetItems(Set.of(item));

        Account account = new Account();
        account.setType(PersonType.EVENT_ORGANIZER);
        Person person = new Person();
        person.setId(UUID.randomUUID());
        account.setPerson(person);

        Authentication auth = new TestingAuthenticationToken(account, null);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));
        when(eventOrganizerRepository.findById(person.getId())).thenReturn(Optional.of(organizer));

        Reservation reservation = new Reservation();

        Product product = mock(Product.class);
        when(product.getPriceAtTimestamp(any())).thenReturn(null);
        reservation.setProduct(product);
        when(reservationRepository.findByEvent(event)).thenReturn(List.of(reservation));

        when(categoryDTOMapper.fromCategoryToSimpleCategoryDTO(category)).thenReturn(new SimpleCategoryDTO());
        when(productDTOMapper.fromProductListToSimpleDTOList(Mockito.anyList())).thenReturn(List.of(new SimpleProductDTO()));

        SimpleEventDTO eventSimpleDTO = new SimpleEventDTO();
        SimpleEventTypeDTO typeDTO = new SimpleEventTypeDTO();
        typeDTO.setId(UUID.randomUUID());
        eventSimpleDTO.setEventType(typeDTO);

        when(eventDTOMapper.fromEventToSimpleDTO(event)).thenReturn(eventSimpleDTO);
        when(categoryService.getCategoriesForEventType(typeDTO.getId())).thenReturn(List.of());
        when(categoryDTOMapper.fromCategoryListToSimpleDTOList(any())).thenReturn(List.of());

        when(product.getCurrentPrice()).thenReturn(new Price());

        BudgetManagementDTO dto = budgetService.findById(eventId);

        assertEquals(200.0, dto.getLeftAmount());
    }



    @Test
    @DisplayName("update - Should update existing budget item amount and persist the event")
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

        Reservation reservation = new Reservation();
        reservation.setProduct(product);

        SimpleEventTypeDTO eventTypeDTO = new SimpleEventTypeDTO();
        eventTypeDTO.setId(UUID.randomUUID());

        SimpleEventDTO eventSimpleDTO = new SimpleEventDTO();
        eventSimpleDTO.setEventType(eventTypeDTO);

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
    @DisplayName("update - Should throw RuntimeException when amount is negative")
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

        RuntimeException exception = assertThrows(RuntimeException.class, () -> budgetService.update(eventId, List.of(dto)));

        assertEquals("Amount cannot be negative", exception.getMessage());
    }

    @Test
    @DisplayName("update - Should throw EntityNotFoundException when event is not found")
    void shouldThrowWhenEventNotFound() {
        UUID eventId = UUID.randomUUID();

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(EntityNotFoundException.class, () -> budgetService.update(eventId, List.of(new UpdateBudgetItemDTO())));

        assertEquals("Event not found", exception.getMessage());
    }

    @Test
    @DisplayName("update - Should throw EntityNotFoundException when organizer not found")
    void shouldThrowWhenOrganizerNotFound() {
        UUID eventId = UUID.randomUUID();

        Account account = new Account();
        account.setType(PersonType.EVENT_ORGANIZER);
        Person person = new Person();
        person.setId(UUID.randomUUID());
        account.setPerson(person);

        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(account, null));

        Event event = new Event();
        event.setId(eventId);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventOrganizerRepository.findById(person.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(EntityNotFoundException.class, () -> budgetService.update(eventId, List.of(new UpdateBudgetItemDTO())));

        assertEquals("Event organizer not found", exception.getMessage());
    }

    @Test
    @DisplayName("update - Should throw AccessDeniedException when organizer does not own event")
    void shouldThrowWhenOrganizerDoesNotOwnEvent() {
        UUID eventId = UUID.randomUUID();

        Event event = new Event();
        event.setId(eventId);

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Collections.emptySet());

        Account account = new Account();
        account.setType(PersonType.EVENT_ORGANIZER);
        Person person = new Person();
        person.setId(UUID.randomUUID());
        account.setPerson(person);

        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(account, null));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventOrganizerRepository.findById(person.getId())).thenReturn(Optional.of(organizer));

        assertThrows(AccessDeniedException.class, () -> budgetService.update(eventId, List.of(new UpdateBudgetItemDTO())));
    }

    @Test
    @DisplayName("update - Should throw EntityNotFoundException when new budget item's category not found")
    void shouldThrowWhenCategoryNotFound() {
        UUID eventId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Event event = new Event();
        event.setId(eventId);
        event.setBudgetItems(new HashSet<>());

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));

        UpdateBudgetItemDTO newItem = new UpdateBudgetItemDTO();
        newItem.setAmount(100.0);
        newItem.setCategoryId(categoryId);

        Account account = new Account();
        account.setType(PersonType.EVENT_ORGANIZER);
        Person person = new Person();
        person.setId(UUID.randomUUID());
        account.setPerson(person);

        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(account, null));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventOrganizerRepository.findById(person.getId())).thenReturn(Optional.of(organizer));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(EntityNotFoundException.class, () -> budgetService.update(eventId, List.of(newItem)));

        assertEquals("Category not found", ex.getMessage());
    }

    @Test
    @DisplayName("update - Should throw EntityNotFoundException when updating non-existing budget item")
    void shouldThrowWhenBudgetItemNotFound() {
        UUID eventId = UUID.randomUUID();
        UUID budgetItemId = UUID.randomUUID();

        UpdateBudgetItemDTO dto = new UpdateBudgetItemDTO();
        dto.setId(budgetItemId);
        dto.setAmount(50.0);

        Event event = new Event();
        event.setId(eventId);
        event.setBudgetItems(new HashSet<>());

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));

        Account account = new Account();
        account.setType(PersonType.EVENT_ORGANIZER);
        Person person = new Person();
        person.setId(UUID.randomUUID());
        account.setPerson(person);

        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(account, null));

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventOrganizerRepository.findById(person.getId())).thenReturn(Optional.of(organizer));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> budgetService.update(eventId, List.of(dto)));

        assertEquals("Budget item not found", exception.getMessage());
    }

    @Test
    @DisplayName("update - Should add new budget item when ID is null")
    void shouldAddNewBudgetItemWhenIdIsNull() {
        UUID eventId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Event event = new Event();
        event.setId(eventId);

        Category category = new Category();
        category.setId(categoryId);

        event.setBudgetItems(new HashSet<>(List.of()));

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));

        UpdateBudgetItemDTO newItem = new UpdateBudgetItemDTO();
        newItem.setId(null);
        newItem.setAmount(200.0);
        newItem.setCategoryId(categoryId);

        Product product = Mockito.mock(Product.class);
        Price price = new Price();

        Reservation reservation = new Reservation();
        reservation.setProduct(product);

        SimpleEventTypeDTO eventTypeDTO = new SimpleEventTypeDTO();
        eventTypeDTO.setId(UUID.randomUUID());

        SimpleEventDTO eventSimpleDTO = new SimpleEventDTO();
        eventSimpleDTO.setEventType(eventTypeDTO);

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
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

        BudgetManagementDTO result = budgetService.update(eventId, List.of(newItem));

        assertNotNull(result);
        assertEquals(200.0, event.getBudgetItems().iterator().next().getAmount());

        verify(eventRepository, times(1)).save(event);
        verify(eventRepository, times(1)).flush();
    }

    @Test
    @DisplayName("update - Should remove budget items not present in update list")
    void shouldRemoveBudgetItemsNotInUpdateList() {
        UUID eventId = UUID.randomUUID();
        UUID budgetItemIdA = UUID.randomUUID();
        UUID budgetItemIdB = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        Event event = new Event();
        event.setId(eventId);

        Category category = new Category();
        category.setId(categoryId);

        BudgetItem existingBudgetItemA = new BudgetItem();
        existingBudgetItemA.setId(budgetItemIdA);
        existingBudgetItemA.setAmount(100.0);
        existingBudgetItemA.setCategory(category);

        BudgetItem existingBudgetItemB = new BudgetItem();
        existingBudgetItemB.setId(budgetItemIdB);
        existingBudgetItemB.setAmount(100.0);
        existingBudgetItemB.setCategory(category);

        event.setBudgetItems(new HashSet<>(List.of(existingBudgetItemA, existingBudgetItemB)));

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));

        UpdateBudgetItemDTO updatedItem = new UpdateBudgetItemDTO();
        updatedItem.setId(budgetItemIdA);
        updatedItem.setAmount(200.0);

        Product product = Mockito.mock(Product.class);
        Price price = new Price();

        Reservation reservation = new Reservation();
        reservation.setProduct(product);

        SimpleEventTypeDTO eventTypeDTO = new SimpleEventTypeDTO();
        eventTypeDTO.setId(UUID.randomUUID());

        SimpleEventDTO eventSimpleDTO = new SimpleEventDTO();
        eventSimpleDTO.setEventType(eventTypeDTO);

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
        assertEquals(1, event.getBudgetItems().size());
        BudgetItem next = event.getBudgetItems().iterator().next();
        assertEquals(budgetItemIdA, next.getId());
        assertEquals(200.0, next.getAmount());

        verify(eventRepository, times(1)).save(event);
        verify(eventRepository, times(1)).flush();
    }

    @Test
    @DisplayName("update - Should throw if category for new budget item doesn't exist")
    void shouldThrowIfNewItemCategoryNotFound() {
        UUID eventId = UUID.randomUUID();
        UUID budgetItemId = UUID.randomUUID();
        UUID categoryId = null;

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

        Reservation reservation = new Reservation();
        reservation.setProduct(product);

        SimpleEventTypeDTO eventTypeDTO = new SimpleEventTypeDTO();
        eventTypeDTO.setId(UUID.randomUUID());

        SimpleEventDTO eventSimpleDTO = new SimpleEventDTO();
        eventSimpleDTO.setEventType(eventTypeDTO);

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
    @DisplayName("Should deny update if account is not organizer")
    void shouldDenyIfUserNotEventOrganizer() {
        Account account = new Account();
        account.setType(PersonType.AUTHENTICATED_USER);

        Authentication auth = new TestingAuthenticationToken(account, null);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);

        SecurityContextHolder.setContext(context);

        UUID eventId = UUID.randomUUID();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(new Event()));

        assertThrows(AccessDeniedException.class, () -> budgetService.update(eventId, Collections.emptyList()));
    }

}
