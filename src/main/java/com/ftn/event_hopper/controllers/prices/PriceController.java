package com.ftn.event_hopper.controllers.prices;

import com.ftn.event_hopper.dtos.prices.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/prices")
public class PriceController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetPriceDTO>> getPrices() {

        Collection<GetPriceDTO> prices = new ArrayList<>() ;

        GetPriceDTO price = new GetPriceDTO();
        price.setId(UUID.randomUUID());
        price.setBasePrice(100);
        price.setDiscount(10);
        price.setFinalPrice(90);
        price.setProductId(UUID.randomUUID());

        GetPriceDTO price2 = new GetPriceDTO();
        price2.setId(UUID.randomUUID());
        price2.setBasePrice(100);
        price2.setDiscount(10);
        price2.setFinalPrice(90);
        price2.setProductId(UUID.randomUUID());

        prices.add(price);
        prices.add(price2);

        return new ResponseEntity<Collection<GetPriceDTO>>(prices, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetPriceDTO> getPrice(@PathVariable("id") UUID id) {
        GetPriceDTO price = new GetPriceDTO();

        if (price == null) {
            return new ResponseEntity<GetPriceDTO>(HttpStatus.NOT_FOUND);
        }

        price.setId(id);
        price.setBasePrice(100);
        price.setDiscount(10);
        price.setFinalPrice(90);
        price.setProductId(UUID.randomUUID());

        return new ResponseEntity<GetPriceDTO>(price, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedPriceDTO> createPrice(@RequestBody CreatePriceDTO price) {
        CreatedPriceDTO createdPrice = new CreatedPriceDTO();

        createdPrice.setId(UUID.randomUUID());
        createdPrice.setBasePrice(price.getBasePrice());
        createdPrice.setDiscount(price.getDiscount());
        createdPrice.setFinalPrice(price.getBasePrice() * ((100 - price.getDiscount()) / 100));
        createdPrice.setProductId(price.getProductId());
        createdPrice.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<CreatedPriceDTO>(createdPrice, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedPriceDTO> updatePrice(@PathVariable("id") UUID id, @RequestBody UpdatePriceDTO price) {
        UpdatedPriceDTO updatedPrice = new UpdatedPriceDTO();

        updatedPrice.setId(UUID.randomUUID());
        updatedPrice.setBasePrice(price.getBasePrice());
        updatedPrice.setDiscount(price.getDiscount());
        updatedPrice.setFinalPrice(price.getBasePrice() * ((100 - price.getDiscount()) / 100));
        updatedPrice.setProductId(price.getProductId());
        updatedPrice.setTimestamp(LocalDateTime.now());

        return new ResponseEntity<UpdatedPriceDTO>(updatedPrice, HttpStatus.OK);
    }

    @GetMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetPriceDTO> getPricesByProductId(@PathVariable("id") UUID id) {

        GetPriceDTO price = new GetPriceDTO();
        price.setId(UUID.randomUUID());
        price.setBasePrice(100);
        price.setDiscount(10);
        price.setFinalPrice(90);
        price.setTimestamp(LocalDateTime.now());
        price.setProductId(id);

        return new ResponseEntity<GetPriceDTO>(price, HttpStatus.OK);
    }

    @GetMapping(value = "/reservation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetPriceDTO> getPriceByReservationId(@PathVariable("id") UUID id) {

        GetPriceDTO price = new GetPriceDTO();
        price.setId(UUID.randomUUID());
        price.setBasePrice(100);
        price.setDiscount(10);
        price.setFinalPrice(90);
        price.setTimestamp(LocalDateTime.now());
        price.setProductId(UUID.randomUUID());

        return new ResponseEntity<GetPriceDTO>(price, HttpStatus.OK);
    }
}
