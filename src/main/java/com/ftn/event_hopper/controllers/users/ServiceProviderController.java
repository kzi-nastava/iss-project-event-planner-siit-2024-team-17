package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.serviceProvider.*;
import com.ftn.event_hopper.services.user.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/service-providers")
public class ServiceProviderController {
    @Autowired
    private ServiceProviderService serviceProviderService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleServiceProviderDTO>> getServiceProviders() {
        List<SimpleServiceProviderDTO> providers = serviceProviderService.findAll();
        if(providers == null) {
            return new ResponseEntity<Collection<SimpleServiceProviderDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(providers, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleServiceProviderDTO> getServiceProvider(@PathVariable UUID id) {
        SimpleServiceProviderDTO provider = serviceProviderService.findOne(id);
        if (provider == null) {
            return new ResponseEntity<SimpleServiceProviderDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(provider, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}/profile", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProfileForServiceProviderDTO> getProfile(@PathVariable UUID id) {
        ProfileForServiceProviderDTO profile = serviceProviderService.getProfile(id);
        if (profile == null) {
            return new ResponseEntity<ProfileForServiceProviderDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedServiceProviderDTO> createServiceProvider(@RequestBody CreateServiceProviderDTO providerDTO) {
        return new ResponseEntity<>(serviceProviderService.create(providerDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedServiceProviderDTO> updatePerson(@PathVariable UUID id, @RequestBody UpdateServiceProviderDTO providerDTO) {
        UpdatedServiceProviderDTO updatedProvider = serviceProviderService.update(id, providerDTO);
        if(updatedProvider == null) {
            return new ResponseEntity<UpdatedServiceProviderDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedProvider, HttpStatus.OK);
    }


}
