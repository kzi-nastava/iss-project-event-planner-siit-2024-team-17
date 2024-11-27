package com.ftn.event_hopper.controllers.solutions;


import com.ftn.event_hopper.dtos.events.GetEventDTO;
import com.ftn.event_hopper.dtos.solutions.GetProductDTO;
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
@RequestMapping("/api/solutions")
public class SolutionController{

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetProductDTO>> getSolutions(){
        Collection<GetProductDTO> solutions = new ArrayList<>();

        return new ResponseEntity<Collection<GetProductDTO>>(solutions, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetProductDTO> getSolution(@PathVariable UUID id){
        GetProductDTO solution = new GetProductDTO();

        if (solution == null){
            return new ResponseEntity<GetProductDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<GetProductDTO>(solution, HttpStatus.OK);
    }

    @GetMapping(value = "/persons-top-5/{usersId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetProductDTO>> getTop5Solutions(@PathVariable UUID usersId){
        Collection<GetProductDTO> top5Solutions= new ArrayList<>();

        return new ResponseEntity<Collection<GetProductDTO>>(top5Solutions, HttpStatus.OK);
    }


}
