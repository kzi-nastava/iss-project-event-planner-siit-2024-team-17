package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.serviceProvider.SimpleServiceProviderDTO;
import com.ftn.event_hopper.services.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
