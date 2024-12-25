package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.person.*;
import com.ftn.event_hopper.services.users.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimplePersonDTO>> getPersons() {
        List<SimplePersonDTO> accounts = personService.findAll();
        if(accounts == null) {
            return new ResponseEntity<Collection<SimplePersonDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimplePersonDTO> getPerson(@PathVariable UUID id) {
        SimplePersonDTO person = personService.findOne(id);
        if (person == null) {
            return new ResponseEntity<SimplePersonDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person , HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/homepage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HomepageForPersonDTO> getHomepage(@PathVariable UUID id) {
        HomepageForPersonDTO homePageForPerson = personService.getHomepage(id);
        if (homePageForPerson == null) {
            return new ResponseEntity<HomepageForPersonDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(homePageForPerson, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedPersonDTO> createPerson(@RequestBody CreatePersonDTO personDTO) {
        return new ResponseEntity<>(personService.create(personDTO), HttpStatus.CREATED);
    }



    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedPersonDTO> updatePerson(@PathVariable UUID id, @RequestBody UpdatePersonDTO person) {
        UpdatedPersonDTO updatedPersonDTO = personService.update(id, person);
        if(updatedPersonDTO == null){
            return new ResponseEntity<UpdatedPersonDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedPersonDTO, HttpStatus.OK);
    }


    @PostMapping(value = "/{personId}/attending-events/{eventId}")
    public ResponseEntity<Void> addAttendingEvent(
            @PathVariable UUID personId,
            @PathVariable UUID eventId) {
        boolean added = personService.addAttendingEvent(personId, eventId);
        if (!added) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/favorite-solutions/{solutionId}")
    public ResponseEntity<?> addFavoriteSolution(@PathVariable UUID solutionId) {
        personService.addFavoriteSolution(solutionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/favorite-solutions/{solutionId}")
    public ResponseEntity<?> removeFavoriteSolution(@PathVariable UUID solutionId) {
        personService.removeFavoriteSolution(solutionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}