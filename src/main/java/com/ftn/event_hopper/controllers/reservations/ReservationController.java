package com.ftn.event_hopper.controllers.reservations;

import com.ftn.event_hopper.dtos.reservations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetReservationDTO>> getReservations() {
        Collection<GetReservationDTO> reservations = new ArrayList<GetReservationDTO>();

        GetReservationDTO reservation = new GetReservationDTO();
        reservation.setId(UUID.randomUUID());
        reservation.setEventId(UUID.randomUUID());
        reservation.setProductId(UUID.randomUUID());
        reservation.setTimestamp(LocalDateTime.now());
        reservation.setFrom(LocalDateTime.now());
        reservation.setTo(LocalDateTime.now());

        GetReservationDTO reservation2 = new GetReservationDTO();
        reservation2.setId(UUID.randomUUID());
        reservation2.setEventId(UUID.randomUUID());
        reservation2.setProductId(UUID.randomUUID());
        reservation2.setTimestamp(LocalDateTime.now());

        reservations.add(reservation);
        reservations.add(reservation2);

        return new ResponseEntity<Collection<GetReservationDTO>>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetReservationDTO> getReservation(@PathVariable UUID id) {
        GetReservationDTO reservation = new GetReservationDTO();

        if (reservation == null) {
            return new ResponseEntity<GetReservationDTO>(HttpStatus.NOT_FOUND);
        }

        reservation.setId(id);
        reservation.setEventId(UUID.randomUUID());
        reservation.setProductId(UUID.randomUUID());
        reservation.setTimestamp(LocalDateTime.now());
        reservation.setFrom(LocalDateTime.now());
        reservation.setTo(LocalDateTime.now());

        return new ResponseEntity<GetReservationDTO>(reservation, HttpStatus.OK);
    }

    @PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedReservationProductDTO> createReservation(@RequestBody CreateReservationProductDTO reservation) {

        CreatedReservationProductDTO createdReservation = new CreatedReservationProductDTO();

        createdReservation.setId(UUID.randomUUID());
        createdReservation.setEventId(reservation.getEventId());
        createdReservation.setProductId(reservation.getProductId());
        createdReservation.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<CreatedReservationProductDTO>(createdReservation, HttpStatus.CREATED);
    }

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