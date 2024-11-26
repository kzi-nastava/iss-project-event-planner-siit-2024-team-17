package com.ftn.event_hopper.controllers.events;

import com.ftn.event_hopper.dtos.events.GetEventDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetEventDTO>> getEvents(){

        Collection<GetEventDTO> events = new ArrayList<>();

        return new ResponseEntity<Collection<GetEventDTO>>(events, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetEventDTO> getEvent(@PathVariable int id){
        GetEventDTO event = new GetEventDTO();

        if (event == null){
            return new ResponseEntity<GetEventDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<GetEventDTO>(event, HttpStatus.OK);
    }
}
