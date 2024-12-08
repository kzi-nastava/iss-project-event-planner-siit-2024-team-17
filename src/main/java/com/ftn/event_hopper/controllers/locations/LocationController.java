package com.ftn.event_hopper.controllers.locations;

import com.ftn.event_hopper.dtos.location.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<LocationDTO>> getLocations() {
        // Temporarily faking the data
        Collection<LocationDTO> locations = new ArrayList<>();

        LocationDTO location1 = new LocationDTO();
        location1.setId(UUID.randomUUID());
        location1.setCity("Trebinje");
        location1.setAddress("Ulica 1");
        location1.setLatitude(42.7111);
        location1.setLongitude(18.3444);
        
        LocationDTO location2 = new LocationDTO();
        location2.setId(UUID.randomUUID());
        location2.setCity("Trebinje");
        location2.setAddress("Ulica 1");
        location2.setLatitude(42.7111);
        location2.setLongitude(18.3444);

        locations.add(location1);
        locations.add(location2);

        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationDTO> getLocation(@PathVariable UUID id) {
        // Temporarily faking the data
        LocationDTO location = new LocationDTO();

        if (location == null) {
            return new ResponseEntity<LocationDTO>(HttpStatus.NOT_FOUND);
        }

        location.setId(id);
        location.setCity("Trebinje");
        location.setAddress("Ulica 1");
        location.setLatitude(42.7111);
        location.setLongitude(18.3444);

        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedLocationDTO> createLocation(@RequestBody CreateLocationDTO location) {
        // Creating a new event location with the provided data
        CreatedLocationDTO createdLocation = new CreatedLocationDTO();

        createdLocation.setId(UUID.randomUUID());
        createdLocation.setCity(location.getCity());
        createdLocation.setAddress(location.getAddress());
        createdLocation.setLatitude(location.getLatitude());
        createdLocation.setLongitude(location.getLongitude());

        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedLocationDTO> updateLocation(@PathVariable UUID id, @RequestBody UpdateLocationDTO location) {
        // Temporarily faking the update process
        UpdatedLocationDTO updatedLocation = new UpdatedLocationDTO();
        updatedLocation.setId(id);
        updatedLocation.setCity(location.getCity());
        updatedLocation.setAddress(location.getAddress());
        updatedLocation.setLatitude(location.getLatitude());
        updatedLocation.setLongitude(location.getLongitude());

        return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
    }

}

