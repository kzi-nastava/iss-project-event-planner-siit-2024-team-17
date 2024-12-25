package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.eventOrganizer.*;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.services.users.EventOrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/event-organizers")
@CrossOrigin(origins = "*")
public class EventOrganizerController {
    @Autowired
    private EventOrganizerService eventOrganizerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleEventOrganizerDTO>> getEventOrganizers() {
        List<SimpleEventOrganizerDTO> organizers = eventOrganizerService.findAll();
        if(organizers == null) {
            return new ResponseEntity<Collection<SimpleEventOrganizerDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(organizers, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleEventOrganizerDTO> getEventOrganizer(@PathVariable UUID id) {
        SimpleEventOrganizerDTO organizer = eventOrganizerService.findOne(id);
        if (organizer == null) {
            return new ResponseEntity<SimpleEventOrganizerDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(organizer, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedEventOrganizerDTO> createEventOrganizer(@RequestBody CreateEventOrganizerDTO organizerDTO) {
        return new ResponseEntity<>(eventOrganizerService.create(organizerDTO), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedEventOrganizerDTO> updateEventOrganizer(@RequestBody UpdateEventOrganizerDTO organizerDTO) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(eventOrganizerService.update(account.getId(), organizerDTO), HttpStatus.OK);
    }

}
