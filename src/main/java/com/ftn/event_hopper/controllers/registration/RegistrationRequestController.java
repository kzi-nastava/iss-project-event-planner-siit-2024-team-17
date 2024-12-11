package com.ftn.event_hopper.controllers.registration;


import com.ftn.event_hopper.dtos.registration.*;
import com.ftn.event_hopper.dtos.users.person.SimplePersonDTO;
import com.ftn.event_hopper.models.registration.RegistrationRequest;
import com.ftn.event_hopper.models.registration.RegistrationRequestStatus;
import com.ftn.event_hopper.services.RegistrationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/registration-requests")
@CrossOrigin(origins = "*")
public class RegistrationRequestController {
    @Autowired
    private RegistrationRequestService registrationRequestService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RegistrationRequestDTO>> getRegistrationRequests() {
        List<RegistrationRequestDTO> requests = registrationRequestService.findAll();
        if(requests == null) {
            return new ResponseEntity<Collection<RegistrationRequestDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistrationRequestDTO> getRegistrationRequest(@PathVariable UUID id) {
        RegistrationRequestDTO registrationRequest = registrationRequestService.findOne(id);
        if (registrationRequest == null) {
            return new ResponseEntity<RegistrationRequestDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(registrationRequest, HttpStatus.OK);
    }

    @GetMapping(value = "/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RegistrationRequestDTO>> getPendingRegistrationRequests() {
        List<RegistrationRequestDTO> requests = registrationRequestService.findAllByStatus(RegistrationRequestStatus.PENDING);
        if(requests == null) {
            return new ResponseEntity<Collection<RegistrationRequestDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/accepted", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RegistrationRequestDTO>> getAcceptedRegistrationRequests() {
        List<RegistrationRequestDTO> requests = registrationRequestService.findAllByStatus(RegistrationRequestStatus.ACCEPTED);
        if(requests == null) {
            return new ResponseEntity<Collection<RegistrationRequestDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping(value = "/rejected", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<RegistrationRequestDTO>> getRejectedRegistrationRequests() {
        List<RegistrationRequestDTO> requests = registrationRequestService.findAllByStatus(RegistrationRequestStatus.REJECTED);
        if(requests == null) {
            return new ResponseEntity<Collection<RegistrationRequestDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedRegistrationRequestDTO> createRegistrationRequest() {
        return new ResponseEntity<>(registrationRequestService.create(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedRegistrationRequestDTO> updateRegistrationRequest(@PathVariable UUID id, @RequestBody UpdateRegistrationRequestDTO registrationRequest) {
        return new ResponseEntity<>(registrationRequestService.update(id, registrationRequest), HttpStatus.OK);
    }

}
