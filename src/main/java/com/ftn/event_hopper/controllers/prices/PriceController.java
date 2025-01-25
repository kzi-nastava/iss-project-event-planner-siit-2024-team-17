package com.ftn.event_hopper.controllers.prices;

import com.ftn.event_hopper.dtos.prices.PriceManagementDTO;
import com.ftn.event_hopper.dtos.prices.UpdatePriceDTO;
import com.ftn.event_hopper.dtos.prices.UpdatedPriceDTO;
import com.ftn.event_hopper.services.solutions.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    @Autowired
    private ProductService productService;

    @GetMapping(value = "/management", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPricesForManagement() {
        try {
        Collection<PriceManagementDTO> prices = productService.getPricesForManagement();
        return new ResponseEntity<Collection<PriceManagementDTO>>(prices, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePrice(@PathVariable("productId") UUID productId, @RequestBody UpdatePriceDTO price) {
        try {
            UpdatedPriceDTO updatedPrice = productService.updatePrice(productId, price);
            return new ResponseEntity<UpdatedPriceDTO>(updatedPrice, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
