package com.ftn.event_hopper.controllers.events;


import com.ftn.event_hopper.dtos.PagedResponse;
import com.ftn.event_hopper.dtos.events.*;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.services.events.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleEventDTO>> getEvents(){

        List<SimpleEventDTO> events = eventService.findAll();
        if (events == null){
            return new ResponseEntity<Collection<SimpleEventDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping(value = "/organizer",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleEventDTO>> getOrganizerEvents(){
        List<SimpleEventDTO> events = eventService.findForOrganizer();
        if (events == null){
            return new ResponseEntity<Collection<SimpleEventDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SinglePageEventDTO> getEvent(@PathVariable UUID id){
        SinglePageEventDTO event = eventService.findOne(id);
        if (event == null){
            return new ResponseEntity<SinglePageEventDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping(value = "/persons-top-5", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleEventDTO>> getTop5Events(){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null) {
            return new ResponseEntity<Collection<SimpleEventDTO>>(HttpStatus.NOT_FOUND);
        }

        Collection<SimpleEventDTO> top5Events = eventService.findTop5(account.getId());

        if (top5Events == null){
            return new ResponseEntity<Collection<SimpleEventDTO>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(top5Events, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedResponse<SimpleEventDTO>> getEventsPage(
            Pageable page,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "eventTypeId", required = false) UUID eventTypeId,
            @RequestParam(value = "time", required = false) String time,             //pretrazuje se po datumu, ne i po vremenu
            @RequestParam(value = "searchContent", required = false) String searchContent,
            @RequestParam(value = "sortField", required = false) String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection

    ) {


        Page<SimpleEventDTO> eventsPage = eventService.findAll(page, city, eventTypeId, time, searchContent, sortField, sortDirection);
        List<SimpleEventDTO> events = eventsPage.getContent();

        PagedResponse<SimpleEventDTO> response = new PagedResponse<>(
                events,
                eventsPage.getTotalPages(),
                eventsPage.getTotalElements()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedEventDTO> createEvent(@RequestBody CreateEventDTO event){
        CreatedEventDTO createdEvent = new CreatedEventDTO();


        return new ResponseEntity<CreatedEventDTO>(createdEvent, HttpStatus.CREATED);
    }

}
