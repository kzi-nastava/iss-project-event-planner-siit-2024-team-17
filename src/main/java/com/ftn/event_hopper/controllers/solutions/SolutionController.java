package com.ftn.event_hopper.controllers.solutions;

import com.ftn.event_hopper.dtos.PagedResponse;
import com.ftn.event_hopper.dtos.events.SimpleEventDTO;
import com.ftn.event_hopper.dtos.reports.GetReportDTO;
import com.ftn.event_hopper.dtos.solutions.GetProductDTO;
import com.ftn.event_hopper.dtos.solutions.GetServiceDTO;
import com.ftn.event_hopper.dtos.solutions.SimpleProductDTO;
import com.ftn.event_hopper.models.shared.ProductStatus;
import com.ftn.event_hopper.services.solutions.ProductService;
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
@RequestMapping("/api/solutions")
public class SolutionController{

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ProductService productService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleProductDTO>> getSolutions(){
        Collection<SimpleProductDTO> solutions = productService.findAll();

        if(solutions == null){
            return new ResponseEntity<Collection<SimpleProductDTO>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(solutions, HttpStatus.OK);

    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SimpleProductDTO> getSolution(@PathVariable UUID id){
        SimpleProductDTO solution = productService.findById(id);

        if (solution == null){
            return new ResponseEntity<SimpleProductDTO>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(solution, HttpStatus.OK);
    }

    @GetMapping(value = "/persons-top-5/{usersId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SimpleProductDTO>> getTop5Solutions(@PathVariable UUID usersId){
        Collection<SimpleProductDTO> top5Solutions= productService.findTop5(usersId);

        if(top5Solutions == null){
            return new ResponseEntity<Collection<SimpleProductDTO>>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(top5Solutions, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedResponse<SimpleProductDTO>> getSolutionsPage(
            //kako da odvojim za checkbox product ili sevrice
            Pageable page,
            @RequestParam(value = "isProduct", required = false) boolean isProduct,
            @RequestParam(value = "isService", required = false) boolean isService,
            @RequestParam(value = "categoryId", required = false) UUID categoryId,
            @RequestParam(value = "eventTypeIds", required = false) ArrayList<UUID> eventTypeIds,
            @RequestParam(value = "minPrice", required = false) Double minPrice,
            @RequestParam(value = "maxPrice", required = false) Double maxPrice,
            @RequestParam(value = "searchContent", required = false) String searchContent
            ) {


        Page<SimpleProductDTO> solutionsPage = productService.findAll(page, isProduct, isService, categoryId, eventTypeIds, minPrice, maxPrice, searchContent);
        List<SimpleProductDTO> solutions = solutionsPage.getContent();

        PagedResponse<SimpleProductDTO> response = new PagedResponse<>(
                solutions,
                solutionsPage.getTotalPages(),
                solutionsPage.getTotalElements()
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
