package com.ftn.event_hopper.controllers.events;

import com.ftn.event_hopper.dtos.comments.UpdatedCommentDTO;
import com.ftn.event_hopper.dtos.events.*;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetEventDTO>> getEvents(){

        Collection<GetEventDTO> events = new ArrayList<>();

        GetEventDTO event1 = new GetEventDTO();
        event1.setId(UUID.randomUUID());
        event1.setName("Event 1");
        event1.setDescription("Event Description");
        event1.setMaxAttendance(80);
        event1.setEventPrivacyType(EventPrivacyType.PRIVATE);
        event1.setStartTime(LocalDateTime.now());
        event1.setPicture("picture.jpg");
        event1.setEventTypeId(UUID.randomUUID());
        event1.setAgendaActivityId(UUID.randomUUID());
        event1.setLocationId(UUID.randomUUID());
        event1.setProducts(new ArrayList<>());
        event1.setInvitations(new ArrayList<>());
        event1.setEventOrganizerId(UUID.randomUUID());




        GetEventDTO event2 = new GetEventDTO();
        event2.setId(UUID.randomUUID());
        event2.setName("Event 2");
        event2.setDescription("Event Description 2");
        event2.setMaxAttendance(80);
        event2.setEventPrivacyType(EventPrivacyType.PRIVATE);
        event2.setStartTime(LocalDateTime.now());
        event2.setPicture("picture2.jpg");
        event2.setEventTypeId(UUID.randomUUID());
        event2.setAgendaActivityId(UUID.randomUUID());
        event2.setLocationId(UUID.randomUUID());
        event2.setProducts(new ArrayList<>());
        event2.setInvitations(new ArrayList<>());
        event2.setEventOrganizerId(UUID.randomUUID());

        events.add(event1);
        events.add(event2);

        return new ResponseEntity<Collection<GetEventDTO>>(events, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetEventDTO> getEvent(@PathVariable UUID id){
        GetEventDTO event = new GetEventDTO();

        if (event == null){
            return new ResponseEntity<GetEventDTO>(HttpStatus.NOT_FOUND);
        }

        event.setId(id);
        event.setName("Event 1");
        event.setDescription("Event Description");
        event.setMaxAttendance(80);
        event.setEventPrivacyType(EventPrivacyType.PRIVATE);
        event.setStartTime(LocalDateTime.now());
        event.setPicture("picture.jpg");
        event.setEventTypeId(UUID.randomUUID());
        event.setAgendaActivityId(UUID.randomUUID());
        event.setLocationId(UUID.randomUUID());
        event.setProducts(new ArrayList<>());
        event.setInvitations(new ArrayList<>());
        event.setEventOrganizerId(UUID.randomUUID());

        return new ResponseEntity<GetEventDTO>(event, HttpStatus.OK);
    }

    @GetMapping(value = "/persons-top-5/{usersId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetEventDTO>> getTop5Events(@PathVariable UUID usersId){
        Collection<GetEventDTO> top5Events = new ArrayList<>();

        GetEventDTO event1 = new GetEventDTO();
        event1.setId(UUID.randomUUID());
        event1.setName("Event 1");
        event1.setDescription("Event Description");
        event1.setMaxAttendance(80);
        event1.setEventPrivacyType(EventPrivacyType.PRIVATE);
        event1.setStartTime(LocalDateTime.now());
        event1.setPicture("picture.jpg");
        event1.setEventTypeId(UUID.randomUUID());
        event1.setAgendaActivityId(UUID.randomUUID());
        event1.setLocationId(UUID.randomUUID());
        event1.setProducts(new ArrayList<>());
        event1.setInvitations(new ArrayList<>());
        event1.setEventOrganizerId(UUID.randomUUID());




        GetEventDTO event2 = new GetEventDTO();
        event2.setId(UUID.randomUUID());
        event2.setName("Event 2");
        event2.setDescription("Event Description 2");
        event2.setMaxAttendance(80);
        event2.setEventPrivacyType(EventPrivacyType.PRIVATE);
        event2.setStartTime(LocalDateTime.now());
        event2.setPicture("picture2.jpg");
        event2.setEventTypeId(UUID.randomUUID());
        event2.setAgendaActivityId(UUID.randomUUID());
        event2.setLocationId(UUID.randomUUID());
        event2.setProducts(new ArrayList<>());
        event2.setInvitations(new ArrayList<>());
        event2.setEventOrganizerId(UUID.randomUUID());

        top5Events.add(event1);
        top5Events.add(event2);
        return new ResponseEntity<Collection<GetEventDTO>>(top5Events, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Collection<GetEventDTO>> searchEvents(
            @RequestParam(value = "locationId", required = false) UUID locationId,
            @RequestParam(value = "eventTypeId", required = false) UUID eventTypeId,
            @RequestParam(value = "startTime", required = false) LocalDateTime startTime,
            @RequestParam(value = "searchContent", required = false) String searchContent,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            //@RequestParam(value = "sort", defaultValue = "startTime,asc") ArrayList<String> sort,
            @RequestParam(defaultValue = "true") boolean ascending) {


        Collection<GetEventDTO> filteredEvents = new ArrayList<>();

        GetEventDTO event1 = new GetEventDTO();
        event1.setId(UUID.randomUUID());
        event1.setName("Event 1");
        event1.setDescription("Event Description");
        event1.setMaxAttendance(80);
        event1.setEventPrivacyType(EventPrivacyType.PRIVATE);
        event1.setStartTime(LocalDateTime.now());
        event1.setPicture("picture.jpg");
        event1.setEventTypeId(UUID.randomUUID());
        event1.setAgendaActivityId(UUID.randomUUID());
        event1.setLocationId(UUID.randomUUID());
        event1.setProducts(new ArrayList<>());
        event1.setInvitations(new ArrayList<>());
        event1.setEventOrganizerId(UUID.randomUUID());



        GetEventDTO event2 = new GetEventDTO();
        event2.setId(UUID.randomUUID());
        event2.setName("Event 2");
        event2.setDescription("Event Description 2");
        event2.setMaxAttendance(80);
        event2.setEventPrivacyType(EventPrivacyType.PRIVATE);
        event2.setStartTime(LocalDateTime.now());
        event2.setPicture("picture2.jpg");
        event2.setEventTypeId(UUID.randomUUID());
        event2.setAgendaActivityId(UUID.randomUUID());
        event2.setLocationId(UUID.randomUUID());
        event2.setProducts(new ArrayList<>());
        event2.setInvitations(new ArrayList<>());
        event2.setEventOrganizerId(UUID.randomUUID());

        filteredEvents.add(event1);
        filteredEvents.add(event2);

        return new ResponseEntity<Collection<GetEventDTO>>(filteredEvents, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedEventDTO> createEvent(@RequestBody CreateEventDTO event){
        CreatedEventDTO createdEvent = new CreatedEventDTO();
        createdEvent.setId(UUID.randomUUID());
        createdEvent.setName(event.getName());
        createdEvent.setDescription(event.getDescription());
        createdEvent.setMaxAttendance(event.getMaxAttendance());
        createdEvent.setEventPrivacyType(event.getEventPrivacyType());
        createdEvent.setStartTime(event.getStartTime());
        createdEvent.setEventTypeId(event.getEventTypeId());
        createdEvent.setLocationId(event.getLocationId());
        event.setAgendaActivityId(event.getAgendaActivityId());
        event.setProducts(event.getProducts());
        event.setInvitations(event.getInvitations());
        event.setEventOrganizerId(event.getEventOrganizerId());

        return new ResponseEntity<CreatedEventDTO>(createdEvent, HttpStatus.CREATED);
    }

}
