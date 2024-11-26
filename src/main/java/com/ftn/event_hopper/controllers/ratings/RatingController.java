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

        GetRatingDTO rating = new GetRatingDTO();
        rating.setId(UUID.randomUUID());
        rating.setValue(5);

        GetRatingDTO rating2 = new GetRatingDTO();
        rating2.setId(UUID.randomUUID());
        rating2.setValue(4);

        ratings.add(rating);
        ratings.add(rating2);

        return new ResponseEntity<Collection<GetRatingDTO>>(ratings, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetRatingDTO> getRating(@PathVariable UUID id) {
        GetRatingDTO rating = new GetRatingDTO();

        if (rating == null) {
            return new ResponseEntity<GetRatingDTO>(HttpStatus.NOT_FOUND);
        }

        rating.setId(id);
        rating.setValue(5);

        return new ResponseEntity<GetRatingDTO>(rating, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedRatingDTO> createRating(@RequestBody CreateRatingDTO rating) {
        CreatedRatingDTO createdRating = new CreatedRatingDTO();
        createdRating.setId(UUID.randomUUID());
        createdRating.setValue(rating.getValue());

        return new ResponseEntity<CreatedRatingDTO>(createdRating, HttpStatus.CREATED);
    }

    @GetMapping(value = "/average/event/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Double> getAverageRatingForEvent(@PathVariable UUID id) {
        return new ResponseEntity<Double>(4.5, HttpStatus.OK);
    }

}
