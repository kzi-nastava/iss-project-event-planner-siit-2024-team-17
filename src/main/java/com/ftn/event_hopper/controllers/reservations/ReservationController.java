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


        return new ResponseEntity<Collection<GetReservationDTO>>(reservations, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetReservationDTO> getReservation(@PathVariable UUID id) {
        GetReservationDTO reservation = new GetReservationDTO();

        if (reservation == null) {
            return new ResponseEntity<GetReservationDTO>(HttpStatus.NOT_FOUND);
        }



        return new ResponseEntity<GetReservationDTO>(reservation, HttpStatus.OK);
    }

    @PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedReservationProductDTO> createReservation(@RequestBody CreateReservationProductDTO reservation) {

        CreatedReservationProductDTO createdReservation = new CreatedReservationProductDTO();


        return new ResponseEntity<CreatedReservationProductDTO>(createdReservation, HttpStatus.CREATED);
    }

    @PostMapping(value = "/services", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedReservationServiceDTO> createReservation(@RequestBody CreateReservationServiceDTO reservation) {

        CreatedReservationServiceDTO createdReservation = new CreatedReservationServiceDTO();



        return new ResponseEntity<CreatedReservationServiceDTO>(createdReservation, HttpStatus.CREATED);
    }
}