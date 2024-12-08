package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.person.GetPersonDTO;
import com.ftn.event_hopper.dtos.users.serviceProvider.*;
import com.ftn.event_hopper.models.users.PersonType;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/service-providers")
public class ServiceProviderController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleServiceProviderDTO>> getServiceProviders() {

        return new ResponseEntity<>(providers, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetServiceProviderDTO> getServiceProvider(@PathVariable UUID id) {
        GetServiceProviderDTO provider = new GetServiceProviderDTO();

        if (provider == null) {
            return new ResponseEntity<GetServiceProviderDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(provider, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedServiceProviderDTO> createServiceProvider(@RequestBody CreateServiceProviderDTO provider) {


        return new ResponseEntity<>(createdProvider, HttpStatus.CREATED);
    }



    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedServiceProviderDTO> updateServiceProvider(@PathVariable UUID id, @RequestBody UpdateServiceProviderDTO provider) {
        //temorarily faking the data, should be get by id
        UpdatedServiceProviderDTO existingProvider = new UpdatedServiceProviderDTO();
        if (existingProvider == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(existingProvider, HttpStatus.OK);
    }



    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteServiceProvider(@PathVariable UUID id) {
        //temorarily faking the data, should be get by id

        if (provider == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
