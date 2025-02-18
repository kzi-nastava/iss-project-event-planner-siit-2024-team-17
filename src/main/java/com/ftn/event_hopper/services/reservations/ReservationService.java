package com.ftn.event_hopper.services.reservations;

import com.ftn.event_hopper.dtos.messages.NewChatMessageDTO;
import com.ftn.event_hopper.dtos.reservations.CreateReservationProductDTO;
import com.ftn.event_hopper.dtos.reservations.CreatedReservationProductDTO;
import com.ftn.event_hopper.models.budgets.BudgetItem;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.reservations.Reservation;
import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.EventOrganizer;
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



        this.notifyOfReservation(account, providerAccount, this.generateProductReservationMessage(event, product, account));

        return ret;
    }

    private String generateProductReservationMessage(Event event, Product product, Account account) {
        return String.format(
                "Dear %s,\n Thank you for purchasing our product %s for the event %s. We hope you enjoy it!",
                account.getUsername(),
                product.getName(),
                event.getName()
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
