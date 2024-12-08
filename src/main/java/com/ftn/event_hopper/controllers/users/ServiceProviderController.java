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

        // Temporarily faking the data
        provider.setId(id);
        provider.setName("Charlie");
        provider.setSurname("Davis");
        provider.setPhoneNumber("555-9876");
        provider.setType(PersonType.SERVICE_PROVIDER);
        provider.setCompanyName("Charlie Decor");
        provider.setCompanyEmail("decor@charlie.com");
        provider.setCompanyDescription("Event decoration services.");
        provider.setCompanyPhotos(new String[]{"decor1.jpg", "decor2.jpg"});
        provider.setWorkStart(Time.valueOf("07:00:00"));
        provider.setWorkEnd(Time.valueOf("20:00:00"));
        provider.setCompanyLocationId(UUID.randomUUID());
        provider.setProductsIds(new ArrayList<>());

        provider.setNotificationsIds(new ArrayList<>()); // Mock notifications
        provider.setAttendingEventsIds(new ArrayList<>()); // Mock attending events
        provider.setFavoriteEventsIds(new ArrayList<>()); // Mock favorite events
        provider.setFavoriteProductsIds(new ArrayList<>()); // Mock favorite products


        return new ResponseEntity<>(provider, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedServiceProviderDTO> createServiceProvider(@RequestBody CreateServiceProviderDTO provider) {
        CreatedServiceProviderDTO createdProvider = new CreatedServiceProviderDTO();

        createdProvider.setId(UUID.randomUUID());
        createdProvider.setName(provider.getName());
        createdProvider.setSurname(provider.getSurname());
        createdProvider.setProfilePicture(provider.getProfilePicture());
        createdProvider.setPhoneNumber(provider.getPhoneNumber());
        createdProvider.setType(PersonType.SERVICE_PROVIDER);

        createdProvider.setCompanyName(provider.getCompanyName());
        createdProvider.setCompanyEmail(provider.getCompanyEmail());
        createdProvider.setCompanyDescription(provider.getCompanyDescription());
        createdProvider.setCompanyPhotos(provider.getCompanyPhotos());
        createdProvider.setWorkStart(provider.getWorkStart());
        createdProvider.setCompanyLocationId(provider.getCompanyLocationId());
        createdProvider.setProductsIds(provider.getProductsIds());

        createdProvider.setNotificationsIds(new ArrayList<>()); // Mock notifications
        createdProvider.setAttendingEventsIds(new ArrayList<>()); // Mock attending events
        createdProvider.setFavoriteEventsIds(new ArrayList<>()); // Mock favorite events
        createdProvider.setFavoriteProductsIds(new ArrayList<>()); // Mock favorite products


        return new ResponseEntity<>(createdProvider, HttpStatus.CREATED);
    }



    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedServiceProviderDTO> updateServiceProvider(@PathVariable UUID id, @RequestBody UpdateServiceProviderDTO provider) {
        //temorarily faking the data, should be get by id
        UpdatedServiceProviderDTO existingProvider = new UpdatedServiceProviderDTO();
        if (existingProvider == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existingProvider.setName(provider.getName());
        existingProvider.setSurname(provider.getSurname());
        existingProvider.setProfilePicture(provider.getProfilePicture());
        existingProvider.setPhoneNumber(provider.getPhoneNumber());

        existingProvider.setCompanyDescription(provider.getCompanyDescription());
        existingProvider.setCompanyPhotos(provider.getCompanyPhotos());
        existingProvider.setWorkStart(provider.getWorkStart());
        existingProvider.setCompanyLocationId(provider.getCompanyLocationId());
        existingProvider.setProductsIds(provider.getProductsIds());

        existingProvider.setFavoriteEventsIds(provider.getFavoriteEventsIds());
        existingProvider.setFavoriteProductsIds(provider.getFavoriteProductsIds());
        existingProvider.setNotificationsIds(provider.getNotificationsIds());
        existingProvider.setAttendingEventsIds(provider.getAttendingEventsIds());

        return new ResponseEntity<>(existingProvider, HttpStatus.OK);
    }



    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteServiceProvider(@PathVariable UUID id) {
        //temorarily faking the data, should be get by id
        GetServiceProviderDTO provider = new GetServiceProviderDTO();
        if (provider == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        provider.setWorkEnd(Time.valueOf(LocalTime.now()));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
