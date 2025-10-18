package com.ftn.event_hopper.service.reservations;

import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.reservations.CreateReservationProductDTO;
import com.ftn.event_hopper.dtos.reservations.CreateReservationServiceDTO;
import com.ftn.event_hopper.dtos.reservations.CreatedReservationProductDTO;
import com.ftn.event_hopper.dtos.reservations.CreatedReservationServiceDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.mapper.reservations.ReservationDTOMapper;
import com.ftn.event_hopper.models.budgets.BudgetItem;
import com.ftn.event_hopper.models.categories.Category;
import com.ftn.event_hopper.models.eventTypes.EventType;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.reservations.Reservation;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.solutions.Service;
import com.ftn.event_hopper.models.users.*;
import com.ftn.event_hopper.repositories.events.EventRepository;
import com.ftn.event_hopper.repositories.reservations.ReservationRepository;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import com.ftn.event_hopper.repositories.solutions.ServiceRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.repositories.users.EventOrganizerRepository;
import com.ftn.event_hopper.repositories.users.ServiceProviderRepository;
import com.ftn.event_hopper.services.emails.EmailService;
import com.ftn.event_hopper.services.messages.MessageService;
import com.ftn.event_hopper.services.notifications.NotificationSchedulerService;
import com.ftn.event_hopper.services.reservations.ReservationService;
import com.ftn.event_hopper.services.solutions.ServiceService;
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
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    private ServiceRepository serviceRepository;
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private ServiceProviderRepository serviceProviderRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private NotificationSchedulerService notificationSchedulerService;
    @Mock
    private EmailService emailService;
    @Mock
    private MessageService messageService;
    @Mock
    private ReservationDTOMapper reservationDTOMapper;




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



    @Test
    @DisplayName("bookService - Should successfully book a service")
    void shouldBookServiceSuccessfully() {
        UUID eventId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        CreateReservationServiceDTO dto = new CreateReservationServiceDTO();
        dto.setEventId(eventId);
        dto.setProductId(productId);
        dto.setFrom(LocalDateTime.now().plusHours(1));
        dto.setTo(dto.getFrom().plusMinutes(60));

        UUID personId = ((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPerson().getId();

        EventType eventType = new EventType();
        eventType.setId(UUID.randomUUID());

        Event event = new Event();
        event.setId(eventId);
        event.setEventType(eventType);
        event.setBudgetItems(new HashSet<>());

        Service service = new Service();
        service.setId(productId);
        service.setAvailable(true);
        service.setDeleted(false);
        service.setVisible(true);
        service.setEventTypes(Set.of(eventType));
        service.setDurationMinutes(60);
        service.setPictures(List.of("dummy.jpg"));

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
        savedReservation.setProduct(service);
        savedReservation.setTimestamp(LocalDateTime.now());
        savedReservation.setStartTime(dto.getFrom());
        savedReservation.setEndTime(dto.getTo());


        when(eventOrganizerRepository.findById(personId)).thenReturn(Optional.of(organizer));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(serviceRepository.findById(productId)).thenReturn(Optional.of(service));
        when(productRepository.findById(productId)).thenReturn(Optional.of(service));
        when(serviceProviderRepository.findByProductsContaining(service)).thenReturn(provider);
        when(accountRepository.findByPersonId(provider.getId())).thenReturn(Optional.of(providerAccount));
        when(reservationRepository.save(any())).thenReturn(savedReservation);

        when(emailService.sendSimpleMail(any())).thenReturn("Mail Sent Successfully...");
        doNothing().when(notificationSchedulerService).scheduleNotification(any(), any(), any());
        when(messageService.sendMessage(any())).thenReturn(true);

        CreatedReservationServiceDTO mappedDto = new CreatedReservationServiceDTO();
        mappedDto.setId(savedReservation.getId());
        mappedDto.setEvent(new SimpleEventDTO());
        mappedDto.getEvent().setId(eventId);
        mappedDto.setProduct(new SimpleProductDTO());
        mappedDto.getProduct().setId(productId);
        mappedDto.setTimestamp(savedReservation.getTimestamp());

        when(reservationDTOMapper.fromReservationToCreatedReservationServiceDTO(any(Reservation.class)))
                .thenReturn(mappedDto);


        CreatedReservationServiceDTO result = reservationService.bookService(dto);

        assertNotNull(result);
        assertEquals(savedReservation.getId(), result.getId());
        assertEquals(eventId, result.getEvent().getId());
        assertEquals(productId, result.getProduct().getId());
        assertNotNull(result.getTimestamp());

        verify(reservationRepository).save(any());
    }

    @Test
    @DisplayName("bookService - Should throw if not Authorized")
    void shouldThrowWhenNotAuthorized(){
        Account account = new Account();
        account.setId(UUID.randomUUID());
        account.setType(PersonType.AUTHENTICATED_USER);

        account.setPerson(new Person());
        account.getPerson().setId(UUID.randomUUID());

        Authentication auth = new TestingAuthenticationToken(account, null);
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);

        SecurityContextHolder.setContext(context);
        CreateReservationServiceDTO dto = new CreateReservationServiceDTO();
        dto.setEventId(UUID.randomUUID());
        dto.setProductId(UUID.randomUUID());
        dto.setFrom(LocalDateTime.now().plusHours(1));
        dto.setTo(dto.getFrom().plusHours(1));


        when(eventOrganizerRepository.findById(account.getPerson().getId()))
                .thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                reservationService.bookService(dto)
        );

        assertTrue(exception.getMessage().contains("You must be an event organizer to make a reservation"));


    }

    @Test
    @DisplayName("bookService - Should throw if not Event Organizer")
    void shouldThrowWhenNotEventOrganizer() {
        UUID eventId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        UUID accountId = UUID.randomUUID();

        CreateReservationServiceDTO dto = new CreateReservationServiceDTO();
        dto.setEventId(eventId);
        dto.setProductId(productId);
        dto.setFrom(LocalDateTime.now().plusHours(1));
        dto.setTo(dto.getFrom().plusMinutes(60));

        EventType eventType = new EventType();
        eventType.setId(UUID.randomUUID());

        Event event = new Event();
        event.setId(eventId);
        event.setEventType(eventType);
        event.setBudgetItems(new HashSet<>());

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of());

        Person person = new Person();
        person.setId(accountId);

        Account account = mock(Account.class);
        when(account.getPerson()).thenReturn(person);

        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(account);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(eventOrganizerRepository.findById(accountId)).thenReturn(Optional.of(organizer));
        when(serviceRepository.findById(productId)).thenReturn(Optional.of(new Service()));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                reservationService.bookService(dto)
        );

        assertTrue(exception.getMessage().contains("You must be the organizer of the event to make a reservation"));
    }

    @Test
    @DisplayName("bookService - Should throw if event not found")
    void shouldThrowIfEventNotFound(){
        UUID eventId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        CreateReservationServiceDTO dto = new CreateReservationServiceDTO();
        dto.setEventId(eventId);
        dto.setProductId(productId);
        dto.setFrom(LocalDateTime.now().plusHours(1));
        dto.setTo(dto.getFrom().plusMinutes(60));

        UUID personId = ((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPerson().getId();

        EventType eventType = new EventType();
        eventType.setId(UUID.randomUUID());

        Service service = new Service();
        service.setId(productId);
        service.setAvailable(true);
        service.setDeleted(false);
        service.setVisible(true);
        service.setEventTypes(Set.of(eventType));
        service.setDurationMinutes(60);
        service.setPictures(List.of("dummy.jpg"));

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of());

        ServiceProvider provider = new ServiceProvider();
        provider.setId(UUID.randomUUID());

        Account providerAccount = new Account();
        providerAccount.setVerified(true);
        providerAccount.setActive(true);

        when(eventOrganizerRepository.findById(personId)).thenReturn(Optional.of(organizer));
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> reservationService.bookService(dto));

        System.out.println(exception.getMessage());
        assertTrue(exception.getMessage().contains("Event not found."));
    }

    @Test
    @DisplayName("bookService - Should throw if service not found")
    void shouldThrowIfServiceNotFound(){
        UUID eventId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        CreateReservationServiceDTO dto = new CreateReservationServiceDTO();
        dto.setEventId(eventId);
        dto.setProductId(productId);
        dto.setFrom(LocalDateTime.now().plusHours(1));
        dto.setTo(dto.getFrom().plusMinutes(60));

        UUID personId = ((Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPerson().getId();

        EventType eventType = new EventType();
        eventType.setId(UUID.randomUUID());

        Event event = new Event();
        event.setId(eventId);
        event.setEventType(eventType);
        event.setBudgetItems(new HashSet<>());

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));


        when(eventOrganizerRepository.findById(personId)).thenReturn(Optional.of(organizer));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(serviceRepository.findById(productId)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> reservationService.bookService(dto));

        assertTrue(exception.getMessage().contains("Service not found."));
    }

    @Test
    @DisplayName("bookService - Should throw if service not available")
    void shouldThrowIfServiceNotAvailable(){
        UUID eventId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        CreateReservationServiceDTO dto = new CreateReservationServiceDTO();
        dto.setEventId(eventId);
        dto.setProductId(productId);
        dto.setFrom(LocalDateTime.now().plusHours(1));
        dto.setTo(dto.getFrom().plusMinutes(60));

        EventType eventType = new EventType();
        eventType.setId(UUID.randomUUID());

        Event event = new Event();
        event.setId(eventId);
        event.setEventType(eventType);
        event.setBudgetItems(new HashSet<>());

        Service service = new Service();
        service.setId(productId);
        service.setAvailable(false);
        service.setDeleted(false);
        service.setVisible(true);
        service.setEventTypes(Set.of(eventType));
        service.setDurationMinutes(60);
        service.setPictures(List.of("dummy.jpg"));

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));

        ServiceProvider provider = new ServiceProvider();
        provider.setId(UUID.randomUUID());

        Account providerAccount = new Account();
        providerAccount.setVerified(true);
        providerAccount.setActive(true);

        Account account = mock(Account.class);
        Person person = new Person();
        person.setId(UUID.randomUUID());
        when(account.getPerson()).thenReturn(person);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(account);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(serviceRepository.findById(productId)).thenReturn(Optional.of(service));
        when(eventOrganizerRepository.findById(person.getId())).thenReturn(Optional.of(organizer));
        when(serviceProviderRepository.findByProductsContaining(service)).thenReturn(provider);
        when(accountRepository.findByPersonId(provider.getId())).thenReturn(Optional.of(providerAccount));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> reservationService.bookService(dto));

        assertTrue(exception.getMessage().contains("Service is not available."));

    }

    @Test
    @DisplayName("bookService - Should throw if service not available for event type")
    void shouldThrowIfServiceNotAvailableForEventType(){
        UUID eventId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        CreateReservationServiceDTO dto = new CreateReservationServiceDTO();
        dto.setEventId(eventId);
        dto.setProductId(productId);
        dto.setFrom(LocalDateTime.now().plusHours(1));
        dto.setTo(dto.getFrom().plusMinutes(60));

        EventType eventType = new EventType();
        eventType.setId(UUID.randomUUID());

        Event event = new Event();
        event.setId(eventId);
        event.setEventType(eventType);
        event.setBudgetItems(new HashSet<>());

        Service service = new Service();
        service.setId(productId);
        service.setAvailable(true);
        service.setDeleted(false);
        service.setVisible(true);
        service.setDurationMinutes(60);
        service.setPictures(List.of("dummy.jpg"));

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));

        ServiceProvider provider = new ServiceProvider();
        provider.setId(UUID.randomUUID());

        Account providerAccount = new Account();
        providerAccount.setVerified(true);
        providerAccount.setActive(true);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(serviceRepository.findById(productId)).thenReturn(Optional.of(service));
        when(eventOrganizerRepository.findById(any())).thenReturn(Optional.of(organizer));

        Exception exception = assertThrows(Exception.class, () -> reservationService.bookService(dto));
        assertTrue(exception.getMessage().contains("Service is not available for this event type"));

    }

    @Test
    @DisplayName("bookService - Should throw if date is in past")
    void shouldThrowIfDateInPast(){
        UUID eventId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        CreateReservationServiceDTO dto = new CreateReservationServiceDTO();
        dto.setEventId(eventId);
        dto.setProductId(productId);
        dto.setFrom(LocalDateTime.now().plusDays(-1));
        dto.setTo(dto.getFrom().plusMinutes(60));

        EventType eventType = new EventType();
        eventType.setId(UUID.randomUUID());

        Event event = new Event();
        event.setId(eventId);
        event.setEventType(eventType);
        event.setBudgetItems(new HashSet<>());

        Service service = new Service();
        service.setId(productId);
        service.setAvailable(true);
        service.setDeleted(false);
        service.setVisible(true);
        service.setEventTypes(Set.of(eventType));
        service.setDurationMinutes(60);
        service.setPictures(List.of("dummy.jpg"));

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));

        ServiceProvider provider = new ServiceProvider();
        provider.setId(UUID.randomUUID());

        Account providerAccount = new Account();
        providerAccount.setVerified(true);
        providerAccount.setActive(true);


        Exception exception = assertThrows(Exception.class, () -> reservationService.bookService(dto));
        assertTrue(exception.getMessage().contains("Service is not available for this date"));
    }

    @Test
    @DisplayName("bookService - Should throw if from is after to")
    void shouldThrowIfFromAfterTo() {

        UUID eventId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();

        CreateReservationServiceDTO dto = new CreateReservationServiceDTO();
        dto.setEventId(eventId);
        dto.setProductId(productId);
        dto.setFrom(LocalDateTime.now().plusDays(2));
        dto.setTo(dto.getFrom().plusMinutes(-60));

        EventType eventType = new EventType();
        eventType.setId(UUID.randomUUID());

        Event event = new Event();
        event.setId(eventId);
        event.setEventType(eventType);
        event.setBudgetItems(new HashSet<>());

        Service service = new Service();
        service.setId(productId);
        service.setAvailable(true);
        service.setDeleted(false);
        service.setVisible(true);
        service.setEventTypes(Set.of(eventType));
        service.setDurationMinutes(60);
        service.setPictures(List.of("dummy.jpg"));

        EventOrganizer organizer = new EventOrganizer();
        organizer.setEvents(Set.of(event));

        ServiceProvider provider = new ServiceProvider();
        provider.setId(UUID.randomUUID());

        Account providerAccount = new Account();
        providerAccount.setVerified(true);
        providerAccount.setActive(true);


        Exception exception = assertThrows(Exception.class, () -> reservationService.bookService(dto));
        System.out.println(exception.getMessage());
        assertTrue(exception.getMessage().contains("Invalid dates"));


    }



}
