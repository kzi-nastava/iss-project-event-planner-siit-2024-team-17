package com.ftn.event_hopper.controllers.solutions;

import com.ftn.event_hopper.dtos.PagedResponse;
import com.ftn.event_hopper.dtos.solutions.*;
import com.ftn.event_hopper.services.solutions.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") UUID id) {
        boolean deleted = serviceService.deleteService(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody CreateServiceDTO service) {
        try {
        CreatedServiceDTO createdService = serviceService.create(service);
        return new ResponseEntity<CreatedServiceDTO>(createdService, HttpStatus.CREATED);
        }  catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@RequestBody UpdateServiceDTO service, @PathVariable UUID id) {
        try {
        UpdatedServiceDTO updatedService = serviceService.update(service, id);

        return new ResponseEntity<UpdatedServiceDTO>(updatedService, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/management")
    public ResponseEntity<?> searchEvents(
            Pageable page,
            @RequestParam(value = "categoryId", required = false) UUID categoryId,
            @RequestParam(value = "eventTypeIds", required = false) List<UUID> eventTypeIds,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "isAvailable", required = false) Boolean isAvailable,
            @RequestParam(value = "searchContent", required = false) String searchContent,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection
    ){
        try {
        Page<ServiceManagementDTO> paged = serviceService.searchServicesForManagement(
                page,
                categoryId,
                eventTypeIds,
                minPrice,
                maxPrice,
                isAvailable,
                searchContent,
                sortField,
                sortDirection
        );
        List<ServiceManagementDTO> services = paged.getContent();

        PagedResponse<ServiceManagementDTO> response = new PagedResponse<>(
                services,
                paged.getTotalPages(),
                paged.getTotalElements()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

}
