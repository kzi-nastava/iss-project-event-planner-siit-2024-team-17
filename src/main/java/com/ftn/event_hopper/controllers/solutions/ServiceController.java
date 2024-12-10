package com.ftn.event_hopper.controllers.solutions;

import com.ftn.event_hopper.dtos.PagedResponse;
import com.ftn.event_hopper.dtos.solutions.*;
import com.ftn.event_hopper.models.shared.ProductStatus;
import com.ftn.event_hopper.services.solutions.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class ServiceController {
    @Autowired
    private ServiceService serviceService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetServiceDTO>> getServices() {

        Collection<GetServiceDTO> services = new ArrayList<>();

        return new ResponseEntity<Collection<GetServiceDTO>>(services, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetServiceDTO> getService() {

        GetServiceDTO service = new GetServiceDTO();

        if (service == null) {
            return new ResponseEntity<GetServiceDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<GetServiceDTO>(service, HttpStatus.OK);
    }

    @GetMapping(value = "/visible", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetServiceDTO>> getVisibleServices() {

        Collection<GetServiceDTO> services = new ArrayList<>();


        return new ResponseEntity<Collection<GetServiceDTO>>(services, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") UUID id) {
        boolean deleted = serviceService.deleteService(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedServiceDTO> createProduct(@RequestBody CreateServiceDTO service) {
        CreatedServiceDTO createdService = serviceService.create(service);

        return new ResponseEntity<CreatedServiceDTO>(createdService, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedServiceDTO> updateProduct(@RequestBody UpdateServiceDTO service, @PathVariable UUID id) {

        UpdatedServiceDTO updatedService = new UpdatedServiceDTO();

        updatedService.setId(id);
        updatedService.setName(service.getName());
        updatedService.setDescription(service.getDescription());
        updatedService.setPictures(service.getPictures());
        updatedService.setAvailable(service.isAvailable());
        updatedService.setVisible(service.isVisible());
        updatedService.setStatus(ProductStatus.APPROVED);
        updatedService.setEditTimestamp(LocalDateTime.now());
        updatedService.setEventTypesIds(service.getEventTypesIds());
        updatedService.setDurationMinutes(service.getDurationMinutes());
        updatedService.setReservationWindowDays(service.getReservationWindowDays());
        updatedService.setCancellationWindowDays(service.getCancellationWindowDays());
        updatedService.setAutoAccept(service.isAutoAccept());

        return new ResponseEntity<UpdatedServiceDTO>(updatedService, HttpStatus.OK);
    }

    @GetMapping("/management")
    public ResponseEntity<PagedResponse<ServiceManagementDTO>> searchEvents(
            Pageable page,
            @RequestParam(value = "categoryId", required = false) UUID categoryId,
            @RequestParam(value = "eventTypeIds", required = false) List<UUID> eventTypeIds,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "isAvailable", required = false) Boolean isAvailable,
            @RequestParam(value = "searchContent", required = false) String searchContent
    ){

        Page<ServiceManagementDTO> paged = serviceService.searchServicesForManagement(
                page,
                categoryId,
                eventTypeIds,
                minPrice,
                maxPrice,
                isAvailable,
                searchContent
        );
        List<ServiceManagementDTO> services = paged.getContent();

        PagedResponse<ServiceManagementDTO> response = new PagedResponse<>(
                services,
                paged.getTotalPages(),
                paged.getTotalElements()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
