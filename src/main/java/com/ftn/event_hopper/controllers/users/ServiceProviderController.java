package com.ftn.event_hopper.controllers.users;

import com.ftn.event_hopper.dtos.users.serviceProvider.*;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.services.users.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // This isnt fetching for personal profile, but when visiting profile in chat
    @GetMapping(value = "/{id}/details", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ServiceProviderDetailsDTO> getServiceProviderDetails(@PathVariable UUID id) {
        ServiceProviderDetailsDTO details = serviceProviderService.getDetails(id);

        if (details == null) {
            return new ResponseEntity<ServiceProviderDetailsDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(details, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SERVICE_PROVIDER')")
    @PostMapping(value = "/change-company-pictures", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeCompanyPictures(@RequestBody List<String> newPictures) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null || account.getType() != PersonType.SERVICE_PROVIDER) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        serviceProviderService.changeCompanyPhotos(account.getPerson().getId(), newPictures);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }



}
