package com.ftn.event_hopper.controllers.users;

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
        // Temporarily faking the data
        Collection<GetEventOrganizerDTO> organizers = new ArrayList<>();

        GetEventOrganizerDTO organizer1 = new GetEventOrganizerDTO();
        organizer1.setId(UUID.randomUUID());
        organizer1.setName("Mapa");
        organizer1.setSurname("Pa");
        organizer1.setPhoneNumber("123-456-7890");
        organizer1.setType(PersonType.EVENT_ORGANIZER);
        organizer1.setLocation(null); // Mock location
        organizer1.setEventsIds(new ArrayList<>());
        organizer1.setProductsIds(new ArrayList<>());
        organizer1.setAccountId(UUID.randomUUID());
        organizer1.setNotificationsIds(new ArrayList<>()); // Mock notifications
        organizer1.setAttendingEventsIds(new ArrayList<>()); // Mock attending events
        organizer1.setFavoriteEventsIds(new ArrayList<>()); // Mock favorite events
        organizer1.setFavoriteProductsIds(new ArrayList<>()); // Mock favorite products

        GetEventOrganizerDTO organizer2 = new GetEventOrganizerDTO();
        organizer2.setId(UUID.randomUUID());
        organizer2.setName("John");
        organizer2.setSurname("Doe");
        organizer2.setPhoneNumber("123-456-7890");
        organizer2.setType(PersonType.EVENT_ORGANIZER);
        organizer2.setLocation(null); // Mock location
        organizer2.setEventsIds(new ArrayList<>());
        organizer2.setProductsIds(new ArrayList<>());
        organizer2.setAccountId(UUID.randomUUID());
        organizer2.setNotificationsIds(new ArrayList<>()); // Mock notifications
        organizer2.setAttendingEventsIds(new ArrayList<>()); // Mock attending events
        organizer2.setFavoriteEventsIds(new ArrayList<>()); // Mock favorite events
        organizer2.setFavoriteProductsIds(new ArrayList<>()); // Mock favorite products

        organizers.add(organizer1);
        organizers.add(organizer2);

        return new ResponseEntity<>(organizers, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetEventOrganizerDTO> getEventOrganizer(@PathVariable UUID id) {
        // Temporarily faking the data
        GetEventOrganizerDTO organizer = new GetEventOrganizerDTO();
        organizer.setId(id);
        organizer.setName("John");
        organizer.setSurname("Doe");
        organizer.setPhoneNumber("123-456-7890");
        organizer.setType(PersonType.EVENT_ORGANIZER);
        organizer.setLocation(null); // Mock location
        organizer.setEventsIds(new ArrayList<>());
        organizer.setProductsIds(new ArrayList<>());
        organizer.setAccountId(UUID.randomUUID());
        organizer.setNotificationsIds(new ArrayList<>()); // Mock notifications
        organizer.setAttendingEventsIds(new ArrayList<>()); // Mock attending events
        organizer.setFavoriteEventsIds(new ArrayList<>()); // Mock favorite events
        organizer.setFavoriteProductsIds(new ArrayList<>()); // Mock favorite products

        return new ResponseEntity<>(organizer, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedEventOrganizerDTO> createEventOrganizer(@RequestBody CreateEventOrganizerDTO organizer) {
        // Creating a new event organizer with the provided data
        CreatedEventOrganizerDTO createdOrganizer = new CreatedEventOrganizerDTO();

        createdOrganizer.setId(UUID.randomUUID());
        createdOrganizer.setName(organizer.getName());
        createdOrganizer.setSurname(organizer.getSurname());
        createdOrganizer.setProfilePicture(organizer.getProfilePicture());
        createdOrganizer.setPhoneNumber(organizer.getPhoneNumber());
        createdOrganizer.setType(PersonType.EVENT_ORGANIZER);
        createdOrganizer.setLocation(organizer.getLocation());
        createdOrganizer.setEventsIds(organizer.getEventsIds());
        createdOrganizer.setProductsIds(organizer.getProductsIds());
        createdOrganizer.setAccountId(organizer.getAccountId());
        createdOrganizer.setNotificationsIds(new ArrayList<>()); // Mock notifications
        createdOrganizer.setAttendingEventsIds(new ArrayList<>()); // Mock attending events
        createdOrganizer.setFavoriteEventsIds(new ArrayList<>()); // Mock favorite events
        createdOrganizer.setFavoriteProductsIds(new ArrayList<>()); // Mock favorite products

        return new ResponseEntity<>(createdOrganizer, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedEventOrganizerDTO> updateEventOrganizer(@PathVariable UUID id, @RequestBody UpdatedEventOrganizerDTO organizer) {
        // Temporarily faking the update process
        UpdatedEventOrganizerDTO updatedOrganizer = new UpdatedEventOrganizerDTO();
        updatedOrganizer.setId(id);
        updatedOrganizer.setName(organizer.getName());
        updatedOrganizer.setSurname(organizer.getSurname());
        updatedOrganizer.setProfilePicture(organizer.getProfilePicture());
        updatedOrganizer.setPhoneNumber(organizer.getPhoneNumber());
        updatedOrganizer.setType(PersonType.EVENT_ORGANIZER);
        updatedOrganizer.setLocation(organizer.getLocation());
        updatedOrganizer.setEventsIds(organizer.getEventsIds());
        updatedOrganizer.setProductsIds(organizer.getProductsIds());
        updatedOrganizer.setAccountId(organizer.getAccountId());

        return new ResponseEntity<>(updatedOrganizer, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteEventOrganizer(@PathVariable UUID id) {
        // Simulating deletion
        return new ResponseEntity<>("Event Organizer with ID " + id + " deleted successfully.", HttpStatus.OK);
    }
}
