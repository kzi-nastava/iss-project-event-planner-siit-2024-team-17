package com.ftn.event_hopper.controllers.solutions;


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
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ServiceController {

//    @GetMapping(value = "/persons-top-5/{usersId}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Collection<GetEventDTO>> getTop5Solutions(@PathVariable UUID usersId){
//        Collection<GetProductDTO> top5Events = new ArrayList<>();
//
//        return new ResponseEntity<Collection<GetEventDTO>>(top5Events, HttpStatus.OK);
//    }
}
