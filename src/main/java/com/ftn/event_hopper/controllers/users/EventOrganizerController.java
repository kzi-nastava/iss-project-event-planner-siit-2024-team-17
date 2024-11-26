package com.ftn.event_hopper.controllers.users;


import com.ftn.event_hopper.dtos.users.eventOrganizer.CreateEventOrganizerDTO;
import com.ftn.event_hopper.dtos.users.eventOrganizer.CreatedEventOrganizerDTO;
import com.ftn.event_hopper.dtos.users.eventOrganizer.GetEventOrganizerDTO;
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
        Collection<GetEventOrganizerDTO> organizers = new ArrayList<>();

        GetEventOrganizerDTO organizer1 = new GetEventOrganizerDTO();
        organizer1.setId(UUID.randomUUID());
        organizer1.setName("Mapa");
        organizer1.setSurname("Pa");
        organizer1.setPhoneNumber("123-456-7890");
        organizer1.setType("Organizer");
        organizer1.setEventUUIDs(new ArrayList<>());
        organizer1.setProductUUIDs(new ArrayList<>());


        GetEventOrganizerDTO organizer2 = new GetEventOrganizerDTO();
        organizer2.setId(UUID.randomUUID());
        organizer2.setName("John");
        organizer2.setSurname("Doe");
        organizer2.setPhoneNumber("123-456-7890");
        organizer2.setType("Organizer");
        organizer2.setEventUUIDs(new ArrayList<>());
        organizer2.setProductUUIDs(new ArrayList<>());

        organizers.add(organizer2);
        organizers.add(organizer1);

        return new ResponseEntity<Collection<GetEventOrganizerDTO>>(organizers, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetEventOrganizerDTO> getEventOrganizer(@PathVariable UUID id) {
        GetEventOrganizerDTO organizer = new GetEventOrganizerDTO();

        if(organizer == null) {
            return new ResponseEntity<GetEventOrganizerDTO>(HttpStatus.NOT_FOUND);
        }

        organizer.setId(id);
        organizer.setName("John");
        organizer.setSurname("Doe");
        organizer.setPhoneNumber("123-456-7890");
        organizer.setType("Organizer");
        organizer.setEventUUIDs(new ArrayList<>());
        organizer.setProductUUIDs(new ArrayList<>());

        return new ResponseEntity<GetEventOrganizerDTO>(organizer, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedEventOrganizerDTO> createEventOrganizer(@RequestBody CreateEventOrganizerDTO organizer) {

        CreatedEventOrganizerDTO createdOrganizer = new CreatedEventOrganizerDTO();

        createdOrganizer.setId(UUID.randomUUID());
        createdOrganizer.setName(organizer.getName());
        createdOrganizer.setSurname(organizer.getSurname());
        createdOrganizer.setProfilePicture(organizer.getProfilePicture());
        createdOrganizer.setPhoneNumber(organizer.getPhoneNumber());
        createdOrganizer.setType("Organizer");
        createdOrganizer.setLocation(organizer.getLocation());
        createdOrganizer.setEventUUIDs(organizer.getEventUUIDs());
        createdOrganizer.setProductUUIDs(organizer.getProductUUIDs());

        return new ResponseEntity<>(createdOrganizer, HttpStatus.CREATED);
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