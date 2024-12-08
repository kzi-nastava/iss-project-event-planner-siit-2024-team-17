package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.account.GetAccountDTO;
import com.ftn.event_hopper.dtos.users.eventOrganizer.*;
import com.ftn.event_hopper.models.users.PersonType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/event-organizers")
public class EventOrganizerController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetEventOrganizerDTO>> getEventOrganizers() {


        return new ResponseEntity<>(organizers, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetEventOrganizerDTO> getEventOrganizer(@PathVariable UUID id) {


        return new ResponseEntity<>(organizer, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedEventOrganizerDTO> createEventOrganizer(@RequestBody CreateEventOrganizerDTO organizer) {


        return new ResponseEntity<>(createdOrganizer, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedEventOrganizerDTO> updateEventOrganizer(@PathVariable UUID id, @RequestBody UpdateEventOrganizerDTO organizer) {

        return new ResponseEntity<>(updatedOrganizer, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteEventOrganizer(@PathVariable UUID id) {


        return new ResponseEntity<>("Event Organizer with ID " + id + " deleted successfully.", HttpStatus.NO_CONTENT);
    }
}
