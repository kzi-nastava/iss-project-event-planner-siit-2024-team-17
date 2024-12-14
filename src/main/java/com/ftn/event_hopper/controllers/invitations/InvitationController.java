package com.ftn.event_hopper.controllers.invitations;

import com.ftn.event_hopper.dtos.invitations.*;
import com.ftn.event_hopper.dtos.location.*;
import com.ftn.event_hopper.models.invitations.Invitation;
import com.ftn.event_hopper.services.invitations.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/invitations")
@CrossOrigin(origins = "*")
public class InvitationController {
    @Autowired
    private InvitationService invitationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<InvitationDTO>> getInvitations() {
        List<InvitationDTO> invitations = invitationService.findAll();
        if(invitations == null) {
            return new ResponseEntity<Collection<InvitationDTO>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(invitations, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvitationDTO> getInvitation(@PathVariable UUID id) {
        InvitationDTO invitation = invitationService.findById(id);
        if (invitation == null) {
            return new ResponseEntity<InvitationDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(invitation, HttpStatus.OK);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedInvitationDTO> createInvitation(@RequestBody CreateInvitationDTO invitationDTO) {
        return new ResponseEntity<>(invitationService.create(invitationDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedInvitationDTO> updateInvitation(@PathVariable UUID id, @RequestBody UpdateInvitationDTO invitationDTO) {
        UpdatedInvitationDTO updatedInvitation = invitationService.update(id, invitationDTO);
        if(updatedInvitation == null) {
            return new ResponseEntity<UpdatedInvitationDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedInvitation, HttpStatus.OK);
    }

//    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<UpdatedInvitationDTO> rejectInvitation(@PathVariable UUID id, @RequestBody UpdateInvitationDTO invitationDTO) {
//        UpdatedInvitationDTO updatedInvitation = invitationService.update(id, invitationDTO);
//        if(updatedInvitation == null) {
//            return new ResponseEntity<UpdatedInvitationDTO>(HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<>(updatedInvitation, HttpStatus.OK);
//    }
}
