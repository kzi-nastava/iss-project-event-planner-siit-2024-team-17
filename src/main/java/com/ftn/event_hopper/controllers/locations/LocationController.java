package com.ftn.event_hopper.controllers.locations;

import com.ftn.event_hopper.dtos.location.*;
import com.ftn.event_hopper.dtos.registration.RegistrationRequestDTO;
import com.ftn.event_hopper.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private LocationService locationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<LocationDTO>> getLocations() {
        return new ResponseEntity<>(locationService.findAllLocations(), HttpStatus.OK);
    }

    @GetMapping(value = "/simple", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleLocationDTO>> getSimpleLocations() {
        return new ResponseEntity<>(locationService.findAllSimpleLocations(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationDTO> getLocation(@PathVariable UUID id) {
        LocationDTO location = locationService.findOneLocation(id);
        if (location == null) {
            return new ResponseEntity<LocationDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/simple", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleLocationDTO> getSimpleLocation(@PathVariable UUID id) {
        SimpleLocationDTO location = locationService.findOneSimpleLocation(id);
        if (location == null) {
            return new ResponseEntity<SimpleLocationDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(location, HttpStatus.OK);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedLocationDTO> createLocation(@RequestBody CreateLocationDTO locationDTO) {
        return new ResponseEntity<>(locationService.create(locationDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedLocationDTO> updateLocation(@PathVariable UUID id, @RequestBody UpdateLocationDTO locationDTO) {
        return new ResponseEntity<>(locationService.update(id, locationDTO), HttpStatus.OK);
    }

}

