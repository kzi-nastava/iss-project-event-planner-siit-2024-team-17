package com.ftn.event_hopper.controllers.events;

import com.ftn.event_hopper.dtos.eventTypes.*;
import com.ftn.event_hopper.services.categories.CategoryService;
import com.ftn.event_hopper.services.events.EventTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/event-types")
public class EventTypeController {
    @Autowired
    private EventTypeService eventTypeService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventTypeManagementDTO> getEventTypesForManagement() {
        List<SimpleEventTypeDTO> eventTypes = eventTypeService.findAll();
        if(eventTypes == null) {
            return new ResponseEntity<EventTypeManagementDTO>(HttpStatus.NOT_FOUND);
        }
        EventTypeManagementDTO eventTypeManagementDTO = new EventTypeManagementDTO(eventTypes, categoryService.findAllApprovedSimple());
        return new ResponseEntity<>(eventTypeManagementDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/active",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetActiveEventTypesDTO> getActiveEventTypes() {
        List<EventTypeDTO> eventTypes = eventTypeService.findAllActive();
        GetActiveEventTypesDTO eventTypesDTO = new GetActiveEventTypesDTO(eventTypes);
        if(eventTypes == null) {
            return new ResponseEntity<GetActiveEventTypesDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(eventTypesDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleEventTypeDTO> updateEventType(@PathVariable UUID id, @RequestBody UpdateEventTypeDTO updateEventType) {
        try{
            SimpleEventTypeDTO eventType= eventTypeService.update(id, updateEventType);
            return new ResponseEntity<>(eventType, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleEventTypeDTO> createEventType(@RequestBody CreateEventTypeDTO createEventTypeDTO) {
        try{
            SimpleEventTypeDTO eventType = eventTypeService.create(createEventTypeDTO);
            return new ResponseEntity<>(eventType, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deactivateEventType(@PathVariable UUID id) {
        try{
            eventTypeService.deactivate(id);
            return new ResponseEntity<>("EventType deactivated.", HttpStatus.NO_CONTENT);
        }
        catch (Exception e){
            return new ResponseEntity<>("EventType deactivation failed.", HttpStatus.NOT_FOUND);
        }
    }
}
