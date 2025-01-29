package com.ftn.event_hopper.controllers.ratings;

import com.ftn.event_hopper.dtos.ratings.CreateProductRatingDTO;
import com.ftn.event_hopper.dtos.ratings.CreatedProductRatingDTO;
import com.ftn.event_hopper.services.solutions.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final ProductService productService;

    public RatingController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/solution", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedProductRatingDTO> createRating(@RequestBody CreateProductRatingDTO rating) {
        CreatedProductRatingDTO createdRating = productService.rateProduct(rating);

        if (createdRating == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<CreatedProductRatingDTO>(createdRating, HttpStatus.CREATED);
    }

}
