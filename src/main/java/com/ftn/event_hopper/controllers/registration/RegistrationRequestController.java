package com.ftn.event_hopper.controllers.registration;


import com.ftn.event_hopper.dtos.location.GetLocationDTO;
import com.ftn.event_hopper.dtos.registration.*;
import com.ftn.event_hopper.models.registration.RegistrationRequestStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/registration-requests")
public class RegistrationRequestController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetRegistrationRequestDTO>> getRegistrationRequests() {
        // Temporarily faking the data
        Collection<GetRegistrationRequestDTO> registrationRequests = new ArrayList<>();

        GetRegistrationRequestDTO registrationRequest1 = new GetRegistrationRequestDTO();
        registrationRequest1.setId(UUID.randomUUID());
        registrationRequest1.setTimestamp(LocalDateTime.now());
        registrationRequest1.setStatus(RegistrationRequestStatus.PENDING);

        GetRegistrationRequestDTO registrationRequest2 = new GetRegistrationRequestDTO();
        registrationRequest2.setId(UUID.randomUUID());
        registrationRequest2.setTimestamp(LocalDateTime.now());
        registrationRequest2.setStatus(RegistrationRequestStatus.ACCEPTED);

        registrationRequests.add(registrationRequest1);
        registrationRequests.add(registrationRequest2);

        return new ResponseEntity<>(registrationRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/pending", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetRegistrationRequestDTO>> getPendingRegistrationRequests() {
        // Temporarily faking the data
        Collection<GetRegistrationRequestDTO> registrationRequests = new ArrayList<>();

        GetRegistrationRequestDTO registrationRequest1 = new GetRegistrationRequestDTO();
        registrationRequest1.setId(UUID.randomUUID());
        registrationRequest1.setTimestamp(LocalDateTime.now());
        registrationRequest1.setStatus(RegistrationRequestStatus.PENDING);

        GetRegistrationRequestDTO registrationRequest2 = new GetRegistrationRequestDTO();
        registrationRequest2.setId(UUID.randomUUID());
        registrationRequest2.setTimestamp(LocalDateTime.now());
        registrationRequest2.setStatus(RegistrationRequestStatus.PENDING);

        registrationRequests.add(registrationRequest1);
        registrationRequests.add(registrationRequest2);

        return new ResponseEntity<>(registrationRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/accepted", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetRegistrationRequestDTO>> getAcceptedRegistrationRequests() {
        // Temporarily faking the data
        Collection<GetRegistrationRequestDTO> registrationRequests = new ArrayList<>();

        GetRegistrationRequestDTO registrationRequest1 = new GetRegistrationRequestDTO();
        registrationRequest1.setId(UUID.randomUUID());
        registrationRequest1.setTimestamp(LocalDateTime.now());
        registrationRequest1.setStatus(RegistrationRequestStatus.ACCEPTED);

        GetRegistrationRequestDTO registrationRequest2 = new GetRegistrationRequestDTO();
        registrationRequest2.setId(UUID.randomUUID());
        registrationRequest2.setTimestamp(LocalDateTime.now());
        registrationRequest2.setStatus(RegistrationRequestStatus.ACCEPTED);

        registrationRequests.add(registrationRequest1);
        registrationRequests.add(registrationRequest2);

        return new ResponseEntity<>(registrationRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/rejected", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetRegistrationRequestDTO>> getRejectedRegistrationRequests() {
        // Temporarily faking the data
        Collection<GetRegistrationRequestDTO> registrationRequests = new ArrayList<>();

        GetRegistrationRequestDTO registrationRequest1 = new GetRegistrationRequestDTO();
        registrationRequest1.setId(UUID.randomUUID());
        registrationRequest1.setTimestamp(LocalDateTime.now());
        registrationRequest1.setStatus(RegistrationRequestStatus.REJECTED);

        GetRegistrationRequestDTO registrationRequest2 = new GetRegistrationRequestDTO();
        registrationRequest2.setId(UUID.randomUUID());
        registrationRequest2.setTimestamp(LocalDateTime.now());
        registrationRequest2.setStatus(RegistrationRequestStatus.REJECTED);

        registrationRequests.add(registrationRequest1);
        registrationRequests.add(registrationRequest2);

        return new ResponseEntity<>(registrationRequests, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetRegistrationRequestDTO> getRegistrationRequest(@PathVariable UUID id) {
        // Temporarily faking the data
        GetRegistrationRequestDTO registrationRequest = new GetRegistrationRequestDTO();

        if (registrationRequest == null) {
            return new ResponseEntity<GetRegistrationRequestDTO>(HttpStatus.NOT_FOUND);
        }

        registrationRequest.setId(UUID.randomUUID());
        registrationRequest.setTimestamp(LocalDateTime.now());
        registrationRequest.setStatus(RegistrationRequestStatus.ACCEPTED);

        return new ResponseEntity<>(registrationRequest, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedRegistrationRequestDTO> createRegistrationRequest() {
        CreatedRegistrationRequestDTO createdRegistrationRequest = new CreatedRegistrationRequestDTO();

        createdRegistrationRequest.setId(UUID.randomUUID());
        createdRegistrationRequest.setTimestamp(LocalDateTime.now());
        createdRegistrationRequest.setStatus(RegistrationRequestStatus.PENDING);

        return new ResponseEntity<>(createdRegistrationRequest, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedRegistrationRequestDTO> updateRegistrationRequest(@PathVariable UUID id, @RequestBody UpdateRegistrationRequestDTO registrationRequest) {
        // Temporarily faking the update process, should be get by id
        UpdatedRegistrationRequestDTO updatedRegistrationRequest = new UpdatedRegistrationRequestDTO();
        updatedRegistrationRequest.setId(id);
        updatedRegistrationRequest.setStatus(registrationRequest.getStatus());
        updatedRegistrationRequest.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<>(updatedRegistrationRequest, HttpStatus.OK);
    }

}
