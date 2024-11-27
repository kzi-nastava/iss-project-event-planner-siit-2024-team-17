package com.ftn.event_hopper.controllers.solutions;


import com.ftn.event_hopper.dtos.events.GetEventDTO;
import com.ftn.event_hopper.dtos.solutions.GetProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @GetMapping("/search")
    public ResponseEntity<Collection<GetProductDTO>> searchSolutions(
            //kako da odvojim za checkbox product ili sevrice
            @RequestParam(value = "categoryId", required = false) UUID categoryId,
            @RequestParam(value = "eventTypeIds", required = false) ArrayList<UUID> eventTypeIds,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "searchContent", required = false) String searchContent) {



        Collection<GetProductDTO> filteredSolutions = new ArrayList<>();

        return new ResponseEntity<Collection<GetProductDTO>>(filteredSolutions, HttpStatus.OK);
    }


}
