package com.ftn.event_hopper.controllers.reservations;

import com.ftn.event_hopper.dtos.reservations.*;
import com.ftn.event_hopper.services.reservations.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

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

//    @PostMapping(value = "/products", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<CreatedReservationProductDTO> createReservationProduct(@RequestBody CreateReservationProductDTO reservation) {
//
//        //return new ResponseEntity<>(reservationService.createProductReservation(reservation), HttpStatus.CREATED);
//    }

    @PostMapping(value = "/services", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedReservationServiceDTO> createReservationService(@RequestBody CreateReservationServiceDTO reservation) {

        return new ResponseEntity<>(reservationService.createServiceReservation(reservation), HttpStatus.CREATED);
    }
}