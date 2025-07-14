package com.ftn.event_hopper.controllers.events;


import com.ftn.event_hopper.dtos.PagedResponse;
import com.ftn.event_hopper.dtos.events.CreateEventDTO;
import com.ftn.event_hopper.dtos.events.GetEventAgendasDTO;
import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.events.SinglePageEventDTO;
import com.ftn.event_hopper.dtos.invitations.InvitationDTO;
import com.ftn.event_hopper.dtos.users.account.SimpleAccountDTO;
import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.users.Account;
import com.ftn.event_hopper.models.users.PersonType;
import com.ftn.event_hopper.services.events.EventService;
import com.ftn.event_hopper.services.invitations.InvitationService;
import com.ftn.event_hopper.services.users.AccountService;
import com.ftn.event_hopper.services.users.EventOrganizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    private EventService eventService;
    @Autowired
    private EventOrganizerService eventOrganizerService;
    @Autowired
    private InvitationService invitationService;
    @Autowired
    private AccountService accountService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleEventDTO>> getEvents(){

        List<SimpleEventDTO> events = eventService.findAll();
        if (events == null){
            return new ResponseEntity<Collection<SimpleEventDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping(value = "{id}/agenda", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetEventAgendasDTO> getAgendaForEvent(@PathVariable UUID id){
        GetEventAgendasDTO events = eventService.getEventAgendas(id);
        if (events == null){
            return new ResponseEntity<GetEventAgendasDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping(value = "/guest-list/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SimpleAccountDTO>> getGuestList(@PathVariable UUID eventId) {
        List<InvitationDTO> invitation = invitationService.findAttendingEvent(eventId);
        List<String> emails = invitation.stream()
                .map(InvitationDTO::getTargetEmail)
                .toList();

        List<SimpleAccountDTO> accounts = accountService.findByEmails(emails);

        if (accounts == null) {
            return new ResponseEntity<List<SimpleAccountDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accounts, HttpStatus.OK);
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
    public ResponseEntity<CreateEventDTO> createEvent(@RequestBody CreateEventDTO eventDTO){
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(account == null || account.getType() != PersonType.EVENT_ORGANIZER){
            return new ResponseEntity<CreateEventDTO>(HttpStatus.NOT_FOUND);
        }
        Event event = eventService.create(eventDTO);

        eventOrganizerService.addEvent(account.getPerson().getId(), event.getId());

        return new ResponseEntity<CreateEventDTO>(HttpStatus.CREATED);
    }


}
