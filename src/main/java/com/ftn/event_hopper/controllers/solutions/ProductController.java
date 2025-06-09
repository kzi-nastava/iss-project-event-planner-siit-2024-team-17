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
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;


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


    @GetMapping(value = "/service-provider/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetProductDTO>> getServiceProvidersProducts() {
        Collection<GetProductDTO> products = new ArrayList<>();

        GetProductDTO product = new GetProductDTO();
        product.setId(UUID.randomUUID());
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPictures(new ArrayList<>());
        product.setAvailable(true);
        product.setVisible(true);
        product.setStatus(ProductStatus.APPROVED);
        product.setRatingsIds(new ArrayList<>());
        product.setCommentsIds(new ArrayList<>());
        product.setPriceId(UUID.randomUUID());
        product.setServiceProviderId(UUID.randomUUID());
        product.setCategoryId(UUID.randomUUID());
        product.setEventTypesIds(new ArrayList<>());

        GetProductDTO product2 = new GetProductDTO();
        product2.setId(UUID.randomUUID());
        product2.setName("Product 2");
        product2.setDescription("Description 2");
        product2.setPictures(new ArrayList<>());
        product2.setAvailable(true);
        product2.setVisible(true);
        product2.setStatus(ProductStatus.APPROVED);
        product2.setRatingsIds(new ArrayList<>());
        product2.setCommentsIds(new ArrayList<>());
        product2.setPriceId(UUID.randomUUID());
        product2.setServiceProviderId(UUID.randomUUID());
        product2.setCategoryId(UUID.randomUUID());
        product2.setEventTypesIds(new ArrayList<>());

        products.add(product);
        products.add(product2);

        return new ResponseEntity<Collection<GetProductDTO>>(products, HttpStatus.OK);
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetProductDTO> getProduct() {
        GetProductDTO product = new GetProductDTO();

        if (product == null) {
            return new ResponseEntity<GetProductDTO>(HttpStatus.NOT_FOUND);
        }

        product.setId(UUID.randomUUID());
        product.setName("Product 1");
        product.setDescription("Description 1");
        product.setPictures(new ArrayList<>());
        product.setAvailable(true);
        product.setVisible(true);
        product.setStatus(ProductStatus.APPROVED);
        product.setRatingsIds(new ArrayList<>());
        product.setCommentsIds(new ArrayList<>());
        product.setPriceId(UUID.randomUUID());
        product.setServiceProviderId(UUID.randomUUID());
        product.setCategoryId(UUID.randomUUID());
        product.setEventTypesIds(new ArrayList<>());

        return new ResponseEntity<GetProductDTO>(product, HttpStatus.OK);
    }

    @GetMapping(value = "/visible", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetProductDTO>> getVisibleProducts() {
        Collection<GetProductDTO> products = new ArrayList<>();

        return new ResponseEntity<Collection<GetProductDTO>>(products, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedProductDTO> updateProduct(@PathVariable UUID id, @RequestBody UpdateProductDTO product) {
        // Temporarily faking the update process, should call a get by id
        UpdatedProductDTO updatedProduct = new UpdatedProductDTO();

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }



    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatedProductDTO> createProduct(@RequestBody CreateProductDTO product) {
        CreatedProductDTO createdProduct = new CreatedProductDTO();


        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        // Temporarily faking the data, should be a call to a get by id method
        GetProductDTO product = new GetProductDTO();


        return new ResponseEntity<>("Product with ID " + id + " deleted successfully.", HttpStatus.NO_CONTENT);
    }

}
