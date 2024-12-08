package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.eventOrganizer.GetEventOrganizerDTO;
import com.ftn.event_hopper.dtos.users.person.*;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SimplePersonDTO>> getPersons() {
        List<SimplePersonDTO> persons = personService.findAll();
        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetPersonDTO> getPerson(@PathVariable UUID id) {
        // Temporarily faking the data
        GetPersonDTO person = new GetPersonDTO();

        if (person == null) {
            return new ResponseEntity<GetPersonDTO>(HttpStatus.NOT_FOUND);
        }

        person.setId(id);
        person.setName("John");
        person.setSurname("Doe");
        person.setPhoneNumber("123-456-7890");
        person.setType(PersonType.AUTHENTICATED_USER);
        person.setLocationId(UUID.randomUUID()); 
        person.setNotificationsIds(new ArrayList<>()); // Mock notifications
        person.setAttendingEventsIds(new ArrayList<>()); // Mock attending events
        person.setFavoriteEventsIds(new ArrayList<>()); // Mock favorite events
        person.setFavoriteProductsIds(new ArrayList<>()); // Mock favorite products

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedPersonDTO> createPerson(@RequestBody CreatePersonDTO person) {
        // Creating a new event person with the provided data
        CreatedPersonDTO createdPerson = new CreatedPersonDTO();

        createdPerson.setId(UUID.randomUUID());
        createdPerson.setName(person.getName());
        createdPerson.setSurname(person.getSurname());
        createdPerson.setProfilePicture(person.getProfilePicture());
        createdPerson.setPhoneNumber(person.getPhoneNumber());
        createdPerson.setType(person.getType());
        createdPerson.setLocationId(person.getLocationId());
        createdPerson.setNotificationsIds(new ArrayList<>()); // Mock notifications
        createdPerson.setAttendingEventsIds(new ArrayList<>()); // Mock attending events
        createdPerson.setFavoriteEventsIds(new ArrayList<>()); // Mock favorite events
        createdPerson.setFavoriteProductsIds(new ArrayList<>()); // Mock favorite products

        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedPersonDTO> updatePerson(@PathVariable UUID id, @RequestBody UpdatePersonDTO person) {
        // Temporarily faking the update process
        UpdatedPersonDTO updatedPerson = new UpdatedPersonDTO();
        updatedPerson.setId(id);
        updatedPerson.setName(person.getName());
        updatedPerson.setSurname(person.getSurname());
        updatedPerson.setProfilePicture(person.getProfilePicture());
        updatedPerson.setPhoneNumber(person.getPhoneNumber());
        updatedPerson.setType(PersonType.EVENT_ORGANIZER);
        updatedPerson.setLocationId(person.getLocationId());

        return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
    }
    
}
