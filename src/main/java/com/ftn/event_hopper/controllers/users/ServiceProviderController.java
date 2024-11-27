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
    public ResponseEntity<Collection<GetServiceProviderDTO>> getServiceProviders() {
        // temporarily faking the data
        Collection<GetServiceProviderDTO> providers = new ArrayList<>();

        GetServiceProviderDTO provider1 = new GetServiceProviderDTO();
        provider1.setId(UUID.randomUUID());
        provider1.setName("Alice");
        provider1.setSurname("Johnson");
        provider1.setPhoneNumber("555-1234");
        provider1.setType(PersonType.SERVICE_PROVIDER);
        provider1.setCompanyName("Alice Catering");
        provider1.setCompanyEmail("contact@alicecatering.com");
        provider1.setCompanyDescription("High-quality catering services.");
        provider1.setCompanyPhotos(new String[]{"photo1.jpg", "photo2.jpg"});
        provider1.setWorkStart(Time.valueOf("08:00:00"));
        provider1.setWorkEnd(Time.valueOf("18:00:00"));
        provider1.setCompanyLocationId(UUID.randomUUID());
        provider1.setProductsIds(new ArrayList<>());
        provider1.setAccountId(UUID.randomUUID());

        provider1.setNotificationsIds(new ArrayList<>()); // Mock notifications
        provider1.setAttendingEventsIds(new ArrayList<>()); // Mock attending events
        provider1.setFavoriteEventsIds(new ArrayList<>()); // Mock favorite events
        provider1.setFavoriteProductsIds(new ArrayList<>()); // Mock favorite products


        GetServiceProviderDTO provider2 = new GetServiceProviderDTO();
        provider2.setId(UUID.randomUUID());
        provider2.setName("Bob");
        provider2.setSurname("Smith");
        provider2.setPhoneNumber("555-5678");
        provider2.setType(PersonType.SERVICE_PROVIDER);
        provider2.setCompanyName("Bob Rentals");
        provider2.setCompanyEmail("info@bobrentals.com");
        provider2.setCompanyDescription("Event equipment rentals.");
        provider2.setCompanyPhotos(new String[]{"rental1.jpg", "rental2.jpg"});
        provider2.setWorkStart(Time.valueOf("09:00:00"));
        provider2.setWorkEnd(Time.valueOf("17:00:00"));
        provider2.setCompanyLocationId(UUID.randomUUID()); //  location
        provider2.setProductsIds(new ArrayList<>());
        provider2.setAccountId(UUID.randomUUID());

        provider2.setNotificationsIds(new ArrayList<>()); // Mock notifications
        provider2.setAttendingEventsIds(new ArrayList<>()); // Mock attending events
        provider2.setFavoriteEventsIds(new ArrayList<>()); // Mock favorite events
        provider2.setFavoriteProductsIds(new ArrayList<>()); // Mock favorite products


        providers.add(provider1);
        providers.add(provider2);

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
        provider.setAccountId(UUID.randomUUID());

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
        createdProvider.setAccountId(UUID.randomUUID());

        createdProvider.setCompanyName(provider.getCompanyName());
        createdProvider.setCompanyEmail(provider.getCompanyEmail());
        createdProvider.setCompanyDescription(provider.getCompanyDescription());
        createdProvider.setCompanyPhotos(provider.getCompanyPhotos());
        createdProvider.setWorkStart(provider.getWorkStart());
        createdProvider.setCompanyLocationId(provider.getCompanyLocationId());
        createdProvider.setProductsIds(provider.getProductsIds());
        createdProvider.setAccountId(UUID.randomUUID());

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
