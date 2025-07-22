package com.ftn.event_hopper.service.reservations;

import com.ftn.event_hopper.dtos.reservations.CreateReservationProductDTO;
import com.ftn.event_hopper.dtos.reservations.CreatedReservationProductDTO;
import com.ftn.event_hopper.models.budgets.BudgetItem;
import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.reservations.Reservation;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.users.*;
import com.ftn.event_hopper.repositories.events.EventRepository;
import com.ftn.event_hopper.repositories.reservations.ReservationRepository;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.repositories.users.EventOrganizerRepository;
import com.ftn.event_hopper.repositories.users.ServiceProviderRepository;
import com.ftn.event_hopper.services.messages.MessageService;
import com.ftn.event_hopper.services.reservations.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private EventOrganizerRepository eventOrganizerRepository;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ServiceProviderRepository serviceProviderRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private MessageService messageService;

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
    @DisplayName("buyProduct - Should successfully reserve product")
    void shouldReserveProductSuccessfully() {
        UUID eventId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        CreateReservationProductDTO dto = new CreateReservationProductDTO();
        dto.setEventId(eventId);
        dto.setProductId(productId);

        UUID personId = ((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPerson().getId();

        EventType eventType = new EventType();
        eventType.setId(UUID.randomUUID());

        Category category = new Category();
        category.setId(UUID.randomUUID());

        Event event = new Event();
        event.setId(eventId);
        event.setEventType(eventType);
        event.setBudgetItems(new HashSet<>());

        Product product = new Product();
        product.setId(productId);
        product.setAvailable(true);
        product.setDeleted(false);
        product.setVisible(true);
        product.setCategory(category);
        product.setEventTypes(Set.of(eventType));

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));

        ServiceProvider provider = new ServiceProvider();
        provider.setId(UUID.randomUUID());

        Account providerAccount = new Account();
        providerAccount.setVerified(true);
        providerAccount.setActive(true);

        Reservation savedReservation = new Reservation();
        savedReservation.setId(UUID.randomUUID());
        savedReservation.setEvent(event);
        savedReservation.setProduct(product);
        savedReservation.setTimestamp(LocalDateTime.now());

        when(eventOrganizerRepository.findById(personId)).thenReturn(Optional.of(organizer));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(serviceProviderRepository.findByProductsContaining(product)).thenReturn(provider);
        when(accountRepository.findByPersonId(provider.getId())).thenReturn(Optional.of(providerAccount));
        when(reservationRepository.save(any())).thenReturn(savedReservation);
        when(messageService.sendMessage(any())).thenReturn(true);

        CreatedReservationProductDTO result = reservationService.buyProduct(dto);

        assertNotNull(result);
        assertEquals(savedReservation.getId(), result.getId());
        assertEquals(eventId, result.getEventId());
        assertEquals(productId, result.getProductId());
        assertNotNull(result.getTimestamp());

        verify(reservationRepository).save(any());
        verify(reservationRepository).flush();
    }

    @Test
    @DisplayName("buyProduct - Should throw when not Authorized")
    void shouldThrowWhenNotLoggedIn() {
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setType(PersonType.AUTHENTICATED_USER);

        account.setPerson(new Person());
        account.getPerson().setId(UUID.randomUUID());

        Authentication auth = new TestingAuthenticationToken(account, null);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);

        SecurityContextHolder.setContext(context);

        when(eventOrganizerRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> reservationService.buyProduct(new CreateReservationProductDTO()));
    }

    @Test
    @DisplayName("buyProduct - Should throw if product not available")
    void shouldThrowIfProductUnavailable() {
        CreateReservationProductDTO dto = new CreateReservationProductDTO();
        dto.setEventId(UUID.randomUUID());
        dto.setProductId(UUID.randomUUID());

        Event event = new Event();
        event.setId(dto.getEventId());
        event.setEventType(new EventType());
        event.getEventType().setId(UUID.randomUUID());

        Product product = new Product();
        product.setAvailable(false);
        product.setDeleted(false);
        product.setVisible(true);
        product.setEventTypes(Set.of(event.getEventType()));
        product.setId(dto.getProductId());

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));

        when(eventOrganizerRepository.findById(any())).thenReturn(Optional.of(organizer));
        when(eventRepository.findById(dto.getEventId())).thenReturn(Optional.of(event));
        when(productRepository.findById(dto.getProductId())).thenReturn(Optional.of(product));

        assertThrows(EntityNotFoundException.class, () -> reservationService.buyProduct(dto));
    }

    @Test
    @DisplayName("buyProduct - Should add new budget item if event lacks category for product")
    void shouldAddBudgetItemWhenCategoryIsMissing() {
        UUID eventId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        CreateReservationProductDTO dto = new CreateReservationProductDTO();
        dto.setEventId(eventId);
        dto.setProductId(productId);

        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID personId = account.getPerson().getId();

        Category category = new Category();
        category.setId(categoryId);

        EventType eventType = new EventType();
        eventType.setId(UUID.randomUUID());

        Product product = new Product();
        product.setId(productId);
        product.setCategory(category);
        product.setAvailable(true);
        product.setDeleted(false);
        product.setVisible(true);
        product.setEventTypes(Set.of(eventType));

        Event event = new Event();
        event.setId(eventId);
        event.setEventType(eventType);
        event.setBudgetItems(new HashSet<>()); // empty

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));

        ServiceProvider provider = new ServiceProvider();
        provider.setId(UUID.randomUUID());

        Account providerAccount = new Account();
        providerAccount.setActive(true);
        providerAccount.setVerified(true);

        Reservation savedReservation = new Reservation();
        savedReservation.setId(UUID.randomUUID());
        savedReservation.setEvent(event);
        savedReservation.setProduct(product);
        savedReservation.setTimestamp(LocalDateTime.now());

        when(eventOrganizerRepository.findById(personId)).thenReturn(Optional.of(organizer));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(serviceProviderRepository.findByProductsContaining(product)).thenReturn(provider);
        when(accountRepository.findByPersonId(provider.getId())).thenReturn(Optional.of(providerAccount));
        when(reservationRepository.save(any())).thenReturn(savedReservation);

        CreatedReservationProductDTO result = reservationService.buyProduct(dto);

        assertNotNull(result);
        assertEquals(1, event.getBudgetItems().size());

        BudgetItem addedItem = event.getBudgetItems().iterator().next();
        assertEquals(categoryId, addedItem.getCategory().getId());
        assertEquals(0, addedItem.getAmount());

        verify(eventRepository).save(event);
        verify(eventRepository).flush();
    }


}
