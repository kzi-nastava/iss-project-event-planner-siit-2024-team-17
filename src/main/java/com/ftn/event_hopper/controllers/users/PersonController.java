package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.person.CreatePersonDTO;
import com.ftn.event_hopper.dtos.users.person.CreatedPersonDTO;
import com.ftn.event_hopper.dtos.users.person.GetPersonDTO;
import com.ftn.event_hopper.dtos.users.person.UpdatedPersonDTO;
import com.ftn.event_hopper.models.users.PersonType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetPersonDTO>> getPersons() {
        // Temporarily faking the data
        Collection<GetPersonDTO> persons = new ArrayList<>();

        GetPersonDTO person1 = new GetPersonDTO();
        person1.setId(UUID.randomUUID());
        person1.setName("Mapa");
        person1.setSurname("Pa");
        person1.setPhoneNumber("123-456-7890");
        person1.setType(PersonType.AUTHENTICATED_USER);
        person1.setLocation(null); // Mock location
        person1.setAccountId(UUID.randomUUID());
        person1.setNotificationsIds(new ArrayList<>()); // Mock notifications
        person1.setAttendingEventsIds(new ArrayList<>()); // Mock attending events
        person1.setFavoriteEventsIds(new ArrayList<>()); // Mock favorite events
        person1.setFavoriteProductsIds(new ArrayList<>()); // Mock favorite products

        GetPersonDTO person2 = new GetPersonDTO();
        person2.setId(UUID.randomUUID());
        person2.setName("John");
        person2.setSurname("Doe");
        person2.setPhoneNumber("123-456-7890");
        person2.setType(PersonType.AUTHENTICATED_USER);
        person2.setLocation(null); // Mock location
        person2.setAccountId(UUID.randomUUID());
        person2.setNotificationsIds(new ArrayList<>()); // Mock notifications
        person2.setAttendingEventsIds(new ArrayList<>()); // Mock attending events
        person2.setFavoriteEventsIds(new ArrayList<>()); // Mock favorite events
        person2.setFavoriteProductsIds(new ArrayList<>()); // Mock favorite products

        persons.add(person1);
        persons.add(person2);

        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetPersonDTO> getPerson(@PathVariable UUID id) {
        // Temporarily faking the data
        GetPersonDTO person = new GetPersonDTO();
        person.setId(id);
        person.setName("John");
        person.setSurname("Doe");
        person.setPhoneNumber("123-456-7890");
        person.setType(PersonType.AUTHENTICATED_USER);
        person.setLocation(null); // Mock location
        person.setAccountId(UUID.randomUUID());
        person.setNotificationsIds(new ArrayList<>()); // Mock notifications
        person.setAttendingEventsIds(new ArrayList<>()); // Mock attending events
        person.setFavoriteEventsIds(new ArrayList<>()); // Mock favorite events
        person.setFavoriteProductsIds(new ArrayList<>()); // Mock favorite products

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedPersonDTO> createPerson(@RequestBody CreatePersonDTO person) {
        // Creating a new event person with the provided data
        CreatedPersonDTO createdOrganizer = new CreatedPersonDTO();

        createdOrganizer.setId(UUID.randomUUID());
        createdOrganizer.setName(person.getName());
        createdOrganizer.setSurname(person.getSurname());
        createdOrganizer.setProfilePicture(person.getProfilePicture());
        createdOrganizer.setPhoneNumber(person.getPhoneNumber());
        createdOrganizer.setType(PersonType.AUTHENTICATED_USER);
        createdOrganizer.setLocation(person.getLocation());
        createdOrganizer.setAccountId(person.getAccountId());
        createdOrganizer.setNotificationsIds(new ArrayList<>()); // Mock notifications
        createdOrganizer.setAttendingEventsIds(new ArrayList<>()); // Mock attending events
        createdOrganizer.setFavoriteEventsIds(new ArrayList<>()); // Mock favorite events
        createdOrganizer.setFavoriteProductsIds(new ArrayList<>()); // Mock favorite products

        return new ResponseEntity<>(createdOrganizer, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedPersonDTO> updatePerson(@PathVariable UUID id, @RequestBody UpdatedPersonDTO person) {
        // Temporarily faking the update process
        UpdatedPersonDTO updatedOrganizer = new UpdatedPersonDTO();
        updatedOrganizer.setId(id);
        updatedOrganizer.setName(person.getName());
        updatedOrganizer.setSurname(person.getSurname());
        updatedOrganizer.setProfilePicture(person.getProfilePicture());
        updatedOrganizer.setPhoneNumber(person.getPhoneNumber());
        updatedOrganizer.setType(PersonType.EVENT_ORGANIZER);
        updatedOrganizer.setLocation(person.getLocation());
        updatedOrganizer.setAccountId(person.getAccountId());

        return new ResponseEntity<>(updatedOrganizer, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deletePerson(@PathVariable UUID id) {
        // Simulating deletion
        return new ResponseEntity<>("Event Organizer with ID " + id + " deleted successfully.", HttpStatus.OK);
    }
    
    
    
    
    
    
}
