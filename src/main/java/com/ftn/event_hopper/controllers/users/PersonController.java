package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.users.person.*;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.services.users.PersonService;
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



    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedPersonDTO> updatePerson(@RequestBody UpdatePersonDTO person) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UpdatedPersonDTO updatedPersonDTO = personService.update(account.getId(), person);
        if(updatedPersonDTO == null){
            return new ResponseEntity<UpdatedPersonDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedPersonDTO, HttpStatus.OK);
    }


    @PostMapping(value = "/attending-events/{eventId}")
    public ResponseEntity<Void> addAttendingEvent(
            @PathVariable UUID eventId) {

        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean added = personService.addAttendingEvent(account.getPerson().getId(), eventId);
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

    @PostMapping(value = "/favorite-events/{eventId}")
    public ResponseEntity<?> addFavoriteEvent(@PathVariable UUID eventId) {
        personService.addFavoriteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/favorite-events/{eventId}")
    public ResponseEntity<?> removeFavoriteEvent(@PathVariable UUID eventId) {
        personService.removeFavoriteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}