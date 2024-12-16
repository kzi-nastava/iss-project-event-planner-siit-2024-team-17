package com.ftn.event_hopper.controllers.prices;

import com.ftn.event_hopper.dtos.prices.*;
import com.ftn.event_hopper.services.solutions.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/prices")
@CrossOrigin(origins = "*")
public class PriceController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/management", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PriceManagementDTO>> getPricesForManagement() {
        Collection<PriceManagementDTO> prices = productService.getPricesForManagement();
        return new ResponseEntity<Collection<PriceManagementDTO>>(prices, HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetPriceDTO>> getPrices() {

        Collection<GetPriceDTO> prices = new ArrayList<>() ;
        return new ResponseEntity<Collection<GetPriceDTO>>(prices, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetPriceDTO> getPrice(@PathVariable("id") UUID id) {
        GetPriceDTO price = new GetPriceDTO();

        if (price == null) {
            return new ResponseEntity<GetPriceDTO>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<GetPriceDTO>(price, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedPriceDTO> createPrice(@RequestBody CreatePriceDTO price) {
        CreatedPriceDTO createdPrice = new CreatedPriceDTO();

        return new ResponseEntity<CreatedPriceDTO>(createdPrice, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedPriceDTO> updatePrice(@PathVariable("productId") UUID productId, @RequestBody UpdatePriceDTO price) {
        UpdatedPriceDTO updatedPrice = productService.updatePrice(productId, price);

        return new ResponseEntity<UpdatedPriceDTO>(updatedPrice, HttpStatus.OK);
    }

    @GetMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetPriceDTO> getPricesByProductId(@PathVariable("id") UUID id) {

        GetPriceDTO price = new GetPriceDTO();

        if (price == null) {
            return new ResponseEntity<GetPriceDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<GetPriceDTO>(price, HttpStatus.OK);
    }

    @GetMapping(value = "/reservation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetPriceDTO> getPriceByReservationId(@PathVariable("id") UUID id) {

        GetPriceDTO price = new GetPriceDTO();

        if (price == null) {
            return new ResponseEntity<GetPriceDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<GetPriceDTO>(price, HttpStatus.OK);
    }
}
