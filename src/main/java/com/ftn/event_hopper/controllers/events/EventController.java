package com.ftn.event_hopper.controllers.events;

import com.ftn.event_hopper.dtos.events.CreateEventDTO;
import com.ftn.event_hopper.dtos.events.CreatedEventDTO;
import com.ftn.event_hopper.dtos.events.GetEventDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetEventDTO>> getEvents(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String location,
        @RequestParam(required = false) String eventType,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime date,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id,asc") String[] sort){

//        Sort sorting = Sort.unsorted();
//        for (String sortParam : sort) {
//            String[] sortParams = sortParam.split(",");
//            String field = sortParams[0];
//            Sort.Direction direction = sortParams.length > 1 ? Sort.Direction.fromString(sortParams[1]) : Sort.Direction.ASC;
//            sorting = sorting.and(Sort.by(direction, field));
//        }
//
//        // Kreiranje Pageable objekta
//        Pageable pageable = PageRequest.of(page, size, sorting);
//
//        // Pozivanje servisa
//        Page<GetEventDTO> events = eventService.getFilteredEvents(title, location, date, pageable);

        Collection<GetEventDTO> events = new ArrayList<>();

        return new ResponseEntity<Collection<GetEventDTO>>(events, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetEventDTO> getEvent(@PathVariable UUID id){
        GetEventDTO event = new GetEventDTO();

        if (event == null){
            return new ResponseEntity<GetEventDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<GetEventDTO>(event, HttpStatus.OK);
    }

    @GetMapping(value = "/persons-top-5/{usersId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetEventDTO>> getTop5Events(@PathVariable UUID usersId){
        Collection<GetEventDTO> top5Events = new ArrayList<>();

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

        return new ResponseEntity<Collection<GetEventDTO>>(filteredEvents, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedEventDTO> createEvent(@RequestBody CreateEventDTO event){
        CreatedEventDTO createdEvent = new CreatedEventDTO();
        createdEvent.setId(event.getId());
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

//    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<UpdatedEventDTO> updateEvent(@PathVariable UUID id, @RequestBody UpdateEventDTO event) throws Exception{
//
//        UpdatedCommentDTO updatedComment = new UpdatedCommentDTO();
//        updatedComment.setId(id);
//        updatedComment.setStatus(comment.getStatus());
//
//        return new ResponseEntity<UpdatedCommentDTO>(updatedComment, HttpStatus.OK);
//
//
//    }
}
