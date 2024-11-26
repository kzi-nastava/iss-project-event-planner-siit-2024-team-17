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
        organizer1.setEventUUIDs(new ArrayList<>());
        organizer1.setProductUUIDs(new ArrayList<>());
        organizer1.setAccountUUID(UUID.randomUUID());

        GetEventOrganizerDTO organizer2 = new GetEventOrganizerDTO();
        organizer2.setId(UUID.randomUUID());
        organizer2.setName("John");
        organizer2.setSurname("Doe");
        organizer2.setPhoneNumber("123-456-7890");
        organizer2.setType(PersonType.EVENT_ORGANIZER);
        organizer2.setLocation(null); // Mock location
        organizer2.setEventUUIDs(new ArrayList<>());
        organizer2.setProductUUIDs(new ArrayList<>());
        organizer2.setAccountUUID(UUID.randomUUID());

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
        organizer.setEventUUIDs(new ArrayList<>());
        organizer.setProductUUIDs(new ArrayList<>());
        organizer.setAccountUUID(UUID.randomUUID());

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
        createdOrganizer.setEventUUIDs(organizer.getEventUUIDs());
        createdOrganizer.setProductUUIDs(organizer.getProductUUIDs());
        createdOrganizer.setAccountUUID(organizer.getAccountUUID());
        createdOrganizer.setNotificationUUIDs(new ArrayList<>()); // Mock notifications
        createdOrganizer.setAttendingEventUUIDs(new ArrayList<>()); // Mock attending events
        createdOrganizer.setFavoriteEventUUIDs(new ArrayList<>()); // Mock favorite events
        createdOrganizer.setFavoriteProductUUIDs(new ArrayList<>()); // Mock favorite products

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
        updatedOrganizer.setEventUUIDs(organizer.getEventUUIDs());
        updatedOrganizer.setProductUUIDs(organizer.getProductUUIDs());
        updatedOrganizer.setAccountUUID(organizer.getAccountUUID());

        return new ResponseEntity<>(updatedOrganizer, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteEventOrganizer(@PathVariable UUID id) {
        // Simulating deletion
        return new ResponseEntity<>("Event Organizer with ID " + id + " deleted successfully.", HttpStatus.OK);
    }
}



/* Example for creating an event organizer
        {
        "name": "Jane",
        "surname": "Smith",
        "profilePicture": "https://example.com/profile/jane-smith.jpg",
        "phoneNumber": "987-654-3210",
        "type": "Organizer",
        "location": {

        "address": "123 Main St",
        "city": "New York"
        },
        "eventUUIDs": [],
        "productUUIDs": []
        }

*/