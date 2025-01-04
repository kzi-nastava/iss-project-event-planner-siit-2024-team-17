package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.serviceProvider.*;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.services.users.ServiceProviderService;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedServiceProviderDTO> createServiceProvider(@RequestBody CreateServiceProviderDTO providerDTO) {
        return new ResponseEntity<>(serviceProviderService.create(providerDTO), HttpStatus.CREATED);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedServiceProviderDTO> updatePerson(@RequestBody UpdateServiceProviderDTO providerDTO) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UpdatedServiceProviderDTO updatedProvider = serviceProviderService.update(account.getId(), providerDTO);
        if(updatedProvider == null) {
            return new ResponseEntity<UpdatedServiceProviderDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedProvider, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceProviderDetailsDTO> getServiceProviderDetails(@PathVariable UUID id) {
        ServiceProviderDetailsDTO details = serviceProviderService.getDetails(id);

        if (details == null) {
            return new ResponseEntity<ServiceProviderDetailsDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(details, HttpStatus.OK);
    }


}
