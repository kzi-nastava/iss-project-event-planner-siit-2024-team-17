package com.ftn.event_hopper.controllers.solutions;

import com.ftn.event_hopper.dtos.reports.GetReportDTO;
import com.ftn.event_hopper.dtos.solutions.GetProductDTO;
import com.ftn.event_hopper.dtos.solutions.GetServiceDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.models.shared.ProductStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/api/solutions")
public class SolutionController{

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleProductDTO>> getSolutions(){
        Collection<SimpleProductDTO> solutions = new ArrayList<>();


        return new ResponseEntity<>(solutions, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleProductDTO> getSolution(@PathVariable UUID id){
        SimpleProductDTO solution = new SimpleProductDTO();

        if (solution == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(solution, HttpStatus.OK);
    }

    @GetMapping(value = "/persons-top-5/{usersId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetProductDTO>> getTop5Solutions(@PathVariable UUID usersId){
        Collection<GetProductDTO> top5Solutions= new ArrayList<>();


        return new ResponseEntity<>(top5Solutions, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Collection<GetProductDTO>> searchSolutions(
            //kako da odvojim za checkbox product ili sevrice
            @RequestParam(value = "isProduct") boolean isProduct,
            @RequestParam(value = "isService") boolean isService,
            @RequestParam(value = "categoryId", required = false) UUID categoryId,
            @RequestParam(value = "eventTypeIds", required = false) ArrayList<UUID> eventTypeIds,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "searchContent", required = false) String searchContent,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {

        Collection<GetProductDTO> filteredSolutions = new ArrayList<>();

        GetProductDTO productDTO = new GetProductDTO();
        productDTO.setId(UUID.randomUUID());
        productDTO.setName("Test Product");
        productDTO.setDescription("Test Description");
        productDTO.setPictures(new ArrayList<>());
        productDTO.setAvailable(true);
        productDTO.setVisible(true);
        productDTO.setStatus(ProductStatus.APPROVED);
        productDTO.setRatingsIds(new ArrayList<>());
        productDTO.setCommentsIds(new ArrayList<>());
        productDTO.setPriceId(UUID.randomUUID());
        productDTO.setServiceProviderId(UUID.randomUUID());
        productDTO.setCategoryId(UUID.randomUUID());
        productDTO.setEventTypesIds(new ArrayList<>());

        GetServiceDTO serviceDTO = new GetServiceDTO();
        serviceDTO.setId(UUID.randomUUID());
        serviceDTO.setName("Test Product");
        serviceDTO.setDescription("Test Description");
        serviceDTO.setPictures(new ArrayList<>());
        serviceDTO.setAvailable(true);
        serviceDTO.setVisible(true);
        serviceDTO.setStatus(ProductStatus.APPROVED);
        serviceDTO.setRatingsIds(new ArrayList<>());
        serviceDTO.setCommentsIds(new ArrayList<>());
        serviceDTO.setPriceId(UUID.randomUUID());
        serviceDTO.setServiceProviderId(UUID.randomUUID());
        serviceDTO.setCategoryId(UUID.randomUUID());
        serviceDTO.setEventTypesIds(new ArrayList<>());
        serviceDTO.setDurationMinutes(600);
        serviceDTO.setReservationWindowDays(50);
        serviceDTO.setCancellationWindowDays(5);
        serviceDTO.setAutoAccept(false);

        filteredSolutions.add(productDTO);
        filteredSolutions.add(serviceDTO);

        return new ResponseEntity<Collection<GetProductDTO>>(filteredSolutions, HttpStatus.OK);
    }


}
