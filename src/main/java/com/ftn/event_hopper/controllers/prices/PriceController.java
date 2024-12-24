package com.ftn.event_hopper.controllers.prices;

import com.ftn.event_hopper.dtos.prices.*;
import com.ftn.event_hopper.services.solutions.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/management", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PriceManagementDTO>> getPricesForManagement() {
        Collection<PriceManagementDTO> prices = productService.getPricesForManagement();
        return new ResponseEntity<Collection<PriceManagementDTO>>(prices, HttpStatus.OK);
    }

    @PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedPriceDTO> updatePrice(@PathVariable("productId") UUID productId, @RequestBody UpdatePriceDTO price) {
        UpdatedPriceDTO updatedPrice = productService.updatePrice(productId, price);

        return new ResponseEntity<UpdatedPriceDTO>(updatedPrice, HttpStatus.OK);
    }

}
