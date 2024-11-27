package com.ftn.event_hopper.controllers.reservations;

import com.ftn.event_hopper.dtos.reservations.CreateReservationServiceDTO;
import com.ftn.event_hopper.dtos.reservations.CreatedReservationServiceDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @PostMapping(value = "/services", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedReservationServiceDTO> createReservation(@RequestBody CreateReservationServiceDTO reservation) {

        CreatedReservationServiceDTO createdReservation = new CreatedReservationServiceDTO();

        createdReservation.setId(UUID.randomUUID());
        createdReservation.setEventId(reservation.getEventId());
        createdReservation.setProductId(reservation.getProductId());
        createdReservation.setTimestamp(LocalDateTime.now());
        createdReservation.setFrom(reservation.getFrom());
        createdReservation.setTo(reservation.getTo());

        return new ResponseEntity<CreatedReservationServiceDTO>(createdReservation, HttpStatus.CREATED);
    }
}
