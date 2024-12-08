package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.person.*;
import com.ftn.event_hopper.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SimplePersonDTO>> getPersons() {
        return new ResponseEntity<>(personService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/homepage", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HomepageForPersonDTO> getHomepage(@PathVariable UUID id) {
        return new ResponseEntity<>(personService.getHomepage(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfileForPersonDTO> getProfile(@PathVariable UUID id) {
        return new ResponseEntity<>(personService.getProfile(id), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimplePersonDTO> getPerson(@PathVariable UUID id) {
        return new ResponseEntity<>(personService.findOne(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedPersonDTO> createPerson(@RequestBody CreatePersonDTO personDTO) {
        return new ResponseEntity<>(personService.create(personDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedPersonDTO> updatePerson(@PathVariable UUID id, @RequestBody UpdatePersonDTO person) {
        return new ResponseEntity<>(personService.update(id, person), HttpStatus.OK);
    }
    
}
