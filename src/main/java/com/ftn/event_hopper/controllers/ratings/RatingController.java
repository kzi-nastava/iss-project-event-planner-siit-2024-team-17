package com.ftn.event_hopper.controllers.ratings;

import com.ftn.event_hopper.dtos.ratings.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetRatingDTO>> getRatings() {
        Collection<GetRatingDTO> ratings = new ArrayList<GetRatingDTO>();


        return new ResponseEntity<Collection<GetRatingDTO>>(ratings, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetRatingDTO> getRating(@PathVariable UUID id) {
        GetRatingDTO rating = new GetRatingDTO();

        if (rating == null) {
            return new ResponseEntity<GetRatingDTO>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<GetRatingDTO>(rating, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedRatingDTO> createRating(@RequestBody CreateRatingDTO rating) {
        CreatedRatingDTO createdRating = new CreatedRatingDTO();


        return new ResponseEntity<CreatedRatingDTO>(createdRating, HttpStatus.CREATED);
    }

}
