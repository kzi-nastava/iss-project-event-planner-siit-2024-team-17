package com.ftn.event_hopper.controllers.solutions;

import com.ftn.event_hopper.dtos.solutions.GetServiceDTO;
import com.ftn.event_hopper.models.shared.ProductStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
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
}
