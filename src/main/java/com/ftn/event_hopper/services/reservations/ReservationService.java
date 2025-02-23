package com.ftn.event_hopper.services.reservations;

import com.ftn.event_hopper.dtos.reservations.CreateReservationServiceDTO;
import com.ftn.event_hopper.dtos.reservations.CreatedReservationServiceDTO;
import com.ftn.event_hopper.mapper.reservations.ReservationDTOMapper;
import com.ftn.event_hopper.models.emails.Email;
import com.ftn.event_hopper.models.reservations.Reservation;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.EventOrganizer;
import com.ftn.event_hopper.models.users.ServiceProvider;
import com.ftn.event_hopper.repositories.events.EventRepository;
import com.ftn.event_hopper.repositories.reservations.ReservationRepository;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import com.ftn.event_hopper.repositories.solutions.ServiceRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.repositories.users.EventOrganizerRepository;
import com.ftn.event_hopper.repositories.users.ServiceProviderRepository;
import com.ftn.event_hopper.services.emails.EmailService;
import com.ftn.event_hopper.services.messages.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import com.ftn.event_hopper.dtos.messages.NewChatMessageDTO;
import com.ftn.event_hopper.dtos.reservations.CreateReservationProductDTO;
import com.ftn.event_hopper.dtos.reservations.CreatedReservationProductDTO;
import com.ftn.event_hopper.models.budgets.BudgetItem;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.reservations.Reservation;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.EventOrganizer;
import com.ftn.event_hopper.models.users.Person;
import com.ftn.event_hopper.models.users.ServiceProvider;
import com.ftn.event_hopper.repositories.events.EventRepository;
import com.ftn.event_hopper.repositories.reservations.ReservationRepository;
import com.ftn.event_hopper.repositories.solutions.ProductRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.repositories.users.EventOrganizerRepository;
import com.ftn.event_hopper.repositories.users.ServiceProviderRepository;
import com.ftn.event_hopper.services.messages.MessageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ReservationDTOMapper reservationDTOMapper;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ServiceRepository serviceRepository;


    public CreatedReservationServiceDTO bookService(CreateReservationServiceDTO createReservation) {

        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null) {
            throw new EntityNotFoundException("You must be logged in to make a reservation.");
        }

        EventOrganizer eventOrganizer = eventOrganizerRepository.findById(account.getPerson().getId())
                .orElseThrow(() -> new EntityNotFoundException("You must be an event organizer to make a reservation."));

        Event event = eventRepository.findById(createReservation.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found."));

        com.ftn.event_hopper.models.solutions.Service service = serviceRepository.findById(createReservation.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Service not found."));

        if(eventOrganizer.getEvents().stream().noneMatch(e -> e.getId().equals(event.getId()))) {
            throw new EntityNotFoundException("You must be the organizer of the event to make a reservation.");
        }

        if (service.getEventTypes().stream().noneMatch(et -> et.getId().equals(event.getEventType().getId()))) {
            throw new EntityNotFoundException("Service is not available for this event type.");
        }

        ServiceProvider provider = serviceProviderRepository.findByProductsContaining(service);
        if (provider == null) {
            throw new EntityNotFoundException("Service provider is not reachable.");
        }
        Account providerAccount = accountRepository.findByPersonId(provider.getId())
                .orElseThrow(() -> new EntityNotFoundException("Provider account not found."));
        if (!providerAccount.isValid()){
            throw new EntityNotFoundException("Provider account is not valid.");
        }

        if (!service.isAvailable() || service.isDeleted() || !service.isVisible()) {
            throw new EntityNotFoundException("Service is not available.");
        }

        if (event.getBudgetItems().stream().noneMatch(bi -> bi.getCategory().getId().equals(service.getCategory().getId()))) {
            BudgetItem budgetItem = new BudgetItem();
            budgetItem.setCategory(service.getCategory());
            budgetItem.setAmount(0);
            budgetItem.setId(null);
            event.getBudgetItems().add(budgetItem);
            eventRepository.save(event);
            eventRepository.flush();
        }

        Reservation reservation = reservationDTOMapper.fromCreateReservationServiceDTOtoReservation(createReservation);
        reservation.setTimestamp(LocalDateTime.now());
        this.save(reservation);

        sendEmailToOD(reservation);
        sendEmailToPUP(reservation);

        this.notifyOfReservation(account, providerAccount, this.generateServiceReservationMessage(event, service, eventOrganizer, provider));


        return reservationDTOMapper.fromReservationToCreatedReservationServiceDTO(reservation);

    }

    private void sendEmailToPUP(Reservation reservation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String subject = "Reservation " + reservation.getProduct().getName() + " created";
        String body = "We want to inform you that your service is booked on "
                + reservation.getTimestamp().toLocalDate().format(formatter)
                + " from " + reservation.getStartTime().toLocalTime().format(timeFormatter)
                + " to " + reservation.getEndTime().toLocalTime().format(timeFormatter);

        ServiceProvider pup = serviceProviderRepository.findByProductsContaining(reservation.getProduct());
        if(pup != null) {
            Optional<Account> accountOptional = accountRepository.findByPersonId(pup.getId());
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                Email emailToPup = new Email(account.getEmail(), subject, body, reservation.getProduct().getPictures().get(0));
                emailService.sendSimpleMail(emailToPup);
            }
        }

    }

    private void sendEmailToOD(Reservation reservation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String subject = "Reservation " + reservation.getProduct().getName() + " created";
        String body = "We want to inform you that your reservation is conformed! See you on "
                + reservation.getTimestamp().toLocalDate().format(formatter);


        Optional<EventOrganizer> OD = eventOrganizerRepository.findByEventsContaining(reservation.getEvent());

        //da li je bolje preuzeti iz JWT?
        if(OD.isPresent()) {
            EventOrganizer od = OD.get();
            Optional<Account> accountOptional = accountRepository.findByPersonId(od.getId());
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                Email emailToOd = new Email(account.getEmail(), subject, body, reservation.getProduct().getPictures().get(0));
                emailService.sendSimpleMail(emailToOd);
            }
        }
    }

    public Reservation save(Reservation reservation) {
        return this.reservationRepository.save(reservation);
    }

    public CreatedReservationProductDTO buyProduct(CreateReservationProductDTO reservationRequestDTO) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null) {
            throw new EntityNotFoundException("You must be logged in to make a reservation.");
        }

        EventOrganizer eventOrganizer = eventOrganizerRepository.findById(account.getPerson().getId())
                .orElseThrow(() -> new EntityNotFoundException("You must be an event organizer to make a reservation."));

        Event event = eventRepository.findById(reservationRequestDTO.getEventId())
                .orElseThrow(() -> new EntityNotFoundException("Event not found."));

        Product product = productRepository.findById(reservationRequestDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found."));

        if(eventOrganizer.getEvents().stream().noneMatch(e -> e.getId().equals(event.getId()))) {
            throw new EntityNotFoundException("You must be the organizer of the event to make a reservation.");
        }

        if (product.getEventTypes().stream().noneMatch(et -> et.getId().equals(event.getEventType().getId()))) {
            throw new EntityNotFoundException("Product is not available for this event type.");
        }

        ServiceProvider provider = serviceProviderRepository.findByProductsContaining(product);
        if (provider == null) {
            throw new EntityNotFoundException("Product provider is not reachable.");
        }
        Account providerAccount = accountRepository.findByPersonId(provider.getId())
                .orElseThrow(() -> new EntityNotFoundException("Provider account not found."));
        if (!providerAccount.isValid()){
            throw new EntityNotFoundException("Provider account is not valid.");
        }

        if (!product.isAvailable() || product.isDeleted() || !product.isVisible()) {
            throw new EntityNotFoundException("Product is not available.");
        }

        if (event.getBudgetItems().stream().noneMatch(bi -> bi.getCategory().getId().equals(product.getCategory().getId()))) {
            BudgetItem budgetItem = new BudgetItem();
            budgetItem.setCategory(product.getCategory());
            budgetItem.setAmount(0);
            budgetItem.setId(null);
            event.getBudgetItems().add(budgetItem);
            eventRepository.save(event);
            eventRepository.flush();
        }

        Reservation newReservation = new Reservation();
        newReservation.setEvent(event);
        newReservation.setProduct(product);
        newReservation.setTimestamp(LocalDateTime.now());
        Reservation created = reservationRepository.save(newReservation);
        reservationRepository.flush();
        CreatedReservationProductDTO ret = new CreatedReservationProductDTO();
        ret.setId(created.getId());
        ret.setEventId(created.getEvent().getId());
        ret.setProductId(created.getProduct().getId());
        ret.setTimestamp(created.getTimestamp());



        this.notifyOfReservation(account, providerAccount, this.generateProductReservationMessage(event, product, eventOrganizer, provider));

        return ret;
    }

    private String generateProductReservationMessage(Event event, Product product, Person purchaser, ServiceProvider provider) {
        return String.format(
                "Dear %s %s,\n\nThank you for purchasing our product %s for the event %s.\nWe hope you enjoy it!\n\nBest regards,\n%s",
                purchaser.getName(),
                purchaser.getSurname(),
                product.getName(),
                event.getName(),
                provider.getCompanyName()
        );
    }

    private String generateServiceReservationMessage(Event event, com.ftn.event_hopper.models.solutions.Service service, Person purchaser, ServiceProvider provider) {
        return String.format(
                "Dear %s %s,\n\nThank you for booking our service %s for the event %s.\nWe hope you enjoy it!\n\nBest regards,\n%s",
                purchaser.getName(),
                purchaser.getSurname(),
                service.getName(),
                event.getName(),
                provider.getCompanyName()
        );
    }

    private void notifyOfReservation(Account account, Account providerAccount, String messageText) {
        NewChatMessageDTO message = new NewChatMessageDTO();
        message.setRecipient(account.getUsername());
        message.setSender(providerAccount.getUsername());
        message.setContent(messageText);
        messageService.sendMessage(message);
    }
}
