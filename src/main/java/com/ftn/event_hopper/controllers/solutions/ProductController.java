package com.ftn.event_hopper.controllers.solutions;

import com.ftn.event_hopper.dtos.PagedResponse;
import com.ftn.event_hopper.dtos.solutions.*;
import com.ftn.event_hopper.models.shared.ProductStatus;
import com.ftn.event_hopper.services.solutions.ProductService;
import com.ftn.event_hopper.services.solutions.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@PreAuthorize("hasRole('SERVICE_PROVIDER')")
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody CreateProductDTO product) {
        try {
            CreatedProductDTO createdProduct = productService.create(product);
            return new ResponseEntity<CreatedProductDTO>(createdProduct, HttpStatus.CREATED);
        }  catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProduct(@RequestBody UpdateProductDTO product, @PathVariable UUID id) {
        try {
            CreatedProductDTO updatedProduct = productService.update(product, id);

            return new ResponseEntity<CreatedProductDTO>(updatedProduct, HttpStatus.OK);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        boolean deleted = productService.deleteProduct(id);
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/management")
        public ResponseEntity<?> searchProducts(
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
            Page<ProductForManagementDTO> paged = productService.findAllForManagement(
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
            List<ProductForManagementDTO> products = paged.getContent();

            PagedResponse<ProductForManagementDTO> response = new PagedResponse<>(
                    products,
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
