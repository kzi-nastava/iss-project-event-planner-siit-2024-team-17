package com.ftn.event_hopper.services.reservations;

import com.ftn.event_hopper.dtos.reservations.CreateReservationServiceDTO;
import com.ftn.event_hopper.dtos.reservations.CreatedReservationServiceDTO;
import com.ftn.event_hopper.mapper.reservations.ReservationDTOMapper;
import com.ftn.event_hopper.models.emails.Email;
import com.ftn.event_hopper.models.reservations.Reservation;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.EventOrganizer;
import com.ftn.event_hopper.models.users.ServiceProvider;
import com.ftn.event_hopper.repositories.reservations.ReservationRepository;
import com.ftn.event_hopper.repositories.users.AccountRepository;
import com.ftn.event_hopper.repositories.users.EventOrganizerRepository;
import com.ftn.event_hopper.repositories.users.ServiceProviderRepository;
import com.ftn.event_hopper.services.emails.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private EventOrganizerRepository eventOrganizerRepository;
    @Autowired
    private ServiceProviderRepository serviceProviderRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ReservationDTOMapper reservationDTOMapper;
    @Autowired
    private EmailService emailService;


    public CreatedReservationServiceDTO createServiceReservation(CreateReservationServiceDTO createReservation) {
        Reservation reservation = reservationDTOMapper.fromCreateReservationServiceDTOtoReservation(createReservation);

        this.save(reservation);

        sendEmailToOD(reservation);
        sendEmailToPUP(reservation);

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
                Email emailToPup = new Email(account.getEmail(), subject, body, reservation.getProduct().getPictures().getFirst());
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
                Email emailToOd = new Email(account.getEmail(), subject, body, reservation.getProduct().getPictures().getFirst());
                emailService.sendSimpleMail(emailToOd);
            }
        }
    }


    public Reservation save(Reservation reservation) {
        return this.reservationRepository.save(reservation);
    }
}
