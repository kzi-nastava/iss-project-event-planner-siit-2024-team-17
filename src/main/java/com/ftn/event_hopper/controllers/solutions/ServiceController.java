package com.ftn.event_hopper.controllers.solutions;

import com.ftn.event_hopper.dtos.solutions.*;
import com.ftn.event_hopper.models.shared.ProductStatus;
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
public class ServiceController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetServiceDTO>> getServices() {

        Collection<GetServiceDTO> services = new ArrayList<>();

        GetServiceDTO service = new GetServiceDTO();
        service.setId(UUID.randomUUID());
        service.setName("Service 1");
        service.setDescription("Description 1");
        service.setPictures(new ArrayList<>());
        service.setAvailable(true);
        service.setVisible(true);
        service.setStatus(ProductStatus.APPROVED);
        service.setRatingsIds(new ArrayList<>());
        service.setCommentsIds(new ArrayList<>());
        service.setPriceId(UUID.randomUUID());
        service.setServiceProviderId(UUID.randomUUID());
        service.setCategoryId(UUID.randomUUID());
        service.setEventTypesIds(new ArrayList<>());
        service.setDurationMinutes(60);
        service.setReservationWindowDays(7);
        service.setCancellationWindowDays(1);
        service.setAutoAccept(true);

        GetServiceDTO service2 = new GetServiceDTO();
        service2.setId(UUID.randomUUID());
        service2.setName("Service 2");
        service2.setDescription("Description 2");
        service2.setPictures(new ArrayList<>());
        service2.setAvailable(true);
        service2.setVisible(true);
        service2.setStatus(ProductStatus.APPROVED);
        service2.setRatingsIds(new ArrayList<>());
        service2.setCommentsIds(new ArrayList<>());
        service2.setPriceId(UUID.randomUUID());
        service2.setServiceProviderId(UUID.randomUUID());
        service2.setCategoryId(UUID.randomUUID());
        service2.setEventTypesIds(new ArrayList<>());
        service2.setDurationMinutes(120);
        service2.setReservationWindowDays(14);
        service2.setCancellationWindowDays(2);
        service2.setAutoAccept(false);

        services.add(service);
        services.add(service2);

        return new ResponseEntity<Collection<GetServiceDTO>>(services, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetServiceDTO> getService() {

        GetServiceDTO service = new GetServiceDTO();

        if (service == null) {
            return new ResponseEntity<GetServiceDTO>(HttpStatus.NOT_FOUND);
        }

        service.setId(UUID.randomUUID());
        service.setName("Service 1");
        service.setDescription("Description 1");
        service.setPictures(new ArrayList<>());
        service.setAvailable(true);
        service.setVisible(true);
        service.setStatus(ProductStatus.APPROVED);
        service.setRatingsIds(new ArrayList<>());
        service.setCommentsIds(new ArrayList<>());
        service.setPriceId(UUID.randomUUID());
        service.setServiceProviderId(UUID.randomUUID());
        service.setCategoryId(UUID.randomUUID());
        service.setEventTypesIds(new ArrayList<>());
        service.setDurationMinutes(60);
        service.setReservationWindowDays(7);
        service.setCancellationWindowDays(1);
        service.setAutoAccept(true);

        return new ResponseEntity<GetServiceDTO>(service, HttpStatus.OK);
    }

    @GetMapping(value = "/visible", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetServiceDTO>> getVisibleServices() {

        Collection<GetServiceDTO> services = new ArrayList<>();

        GetServiceDTO service = new GetServiceDTO();
        service.setId(UUID.randomUUID());
        service.setName("Service 1");
        service.setDescription("Description 1");
        service.setPictures(new ArrayList<>());
        service.setAvailable(true);
        service.setVisible(true);
        service.setStatus(ProductStatus.APPROVED);
        service.setRatingsIds(new ArrayList<>());
        service.setCommentsIds(new ArrayList<>());
        service.setPriceId(UUID.randomUUID());
        service.setServiceProviderId(UUID.randomUUID());
        service.setCategoryId(UUID.randomUUID());
        service.setEventTypesIds(new ArrayList<>());
        service.setDurationMinutes(60);
        service.setReservationWindowDays(7);
        service.setCancellationWindowDays(1);
        service.setAutoAccept(true);

        GetServiceDTO service2 = new GetServiceDTO();
        service2.setId(UUID.randomUUID());
        service2.setName("Service 2");
        service2.setDescription("Description 2");
        service2.setPictures(new ArrayList<>());
        service2.setAvailable(true);
        service2.setVisible(true);
        service2.setStatus(ProductStatus.APPROVED);
        service2.setRatingsIds(new ArrayList<>());
        service2.setCommentsIds(new ArrayList<>());
        service2.setPriceId(UUID.randomUUID());
        service2.setServiceProviderId(UUID.randomUUID());
        service2.setCategoryId(UUID.randomUUID());
        service2.setEventTypesIds(new ArrayList<>());
        service2.setDurationMinutes(120);
        service2.setReservationWindowDays(14);
        service2.setCancellationWindowDays(2);
        service2.setAutoAccept(false);

        services.add(service);
        services.add(service2);

        return new ResponseEntity<Collection<GetServiceDTO>>(services, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedServiceDTO> createProduct(@RequestBody CreateServiceDTO service) {

        CreatedServiceDTO createdService = new CreatedServiceDTO();

        createdService.setId(UUID.randomUUID());
        createdService.setName(service.getName());
        createdService.setDescription(service.getDescription());
        createdService.setPictures(service.getPictures());
        createdService.setAvailable(service.isAvailable());
        createdService.setVisible(service.isVisible());
        createdService.setStatus(ProductStatus.APPROVED);
        createdService.setEditTimestamp(LocalDateTime.now());
        createdService.setRatingsIds(new ArrayList<>());
        createdService.setCommentsIds(new ArrayList<>());
        createdService.setPriceId(service.getPriceId());
        createdService.setServiceProviderId(service.getServiceProviderId());
        createdService.setCategoryId(service.getCategoryId());
        createdService.setEventTypesIds(service.getEventTypesIds());
        createdService.setDurationMinutes(service.getDurationMinutes());
        createdService.setReservationWindowDays(service.getReservationWindowDays());
        createdService.setCancellationWindowDays(service.getCancellationWindowDays());
        createdService.setAutoAccept(service.isAutoAccept());
        createdService.setDeleted(false);

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

    @GetMapping("/search")
    public ResponseEntity<Collection<GetServiceDTO>> searchEvents(
            @RequestParam(value = "categoryId", required = false) UUID categoryId,
            @RequestParam(value = "eventTypeIds", required = false) List<UUID> eventTypeIds,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "searchContent", required = false) String searchContent,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending){

        Collection<GetServiceDTO> filteredEvents = new ArrayList<>();

        GetServiceDTO service = new GetServiceDTO();
        service.setId(UUID.randomUUID());
        service.setName("Service 1 + " + searchContent);
        service.setDescription("Description 1");
        service.setPictures(new ArrayList<>());
        service.setAvailable(true);
        service.setVisible(true);
        service.setStatus(ProductStatus.APPROVED);
        service.setRatingsIds(new ArrayList<>());
        service.setCommentsIds(new ArrayList<>());
        service.setPriceId(UUID.randomUUID());
        service.setServiceProviderId(UUID.randomUUID());
        service.setCategoryId(categoryId);
        service.setEventTypesIds(eventTypeIds);
        service.setDurationMinutes(60);
        service.setReservationWindowDays(7);
        service.setCancellationWindowDays(1);
        service.setAutoAccept(true);

        filteredEvents.add(service);

        return new ResponseEntity<>(filteredEvents, HttpStatus.OK);
    }
}
