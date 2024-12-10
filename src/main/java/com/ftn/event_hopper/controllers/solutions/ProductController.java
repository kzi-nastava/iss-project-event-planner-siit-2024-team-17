package com.ftn.event_hopper.controllers.solutions;

import com.ftn.event_hopper.dtos.solutions.*;
import com.ftn.event_hopper.models.shared.ProductStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetProductDTO>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) UUID eventTypeId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean isAvailable,
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(required = false) String description) {

        Collection<GetProductDTO> products = new ArrayList<>();

        // Mock data
        GetProductDTO product1 = new GetProductDTO();
        product1.setId(UUID.randomUUID());
        product1.setName("Event Ticket");
        product1.setDescription("VIP Ticket for Concert");
        product1.setAvailable(true);
        product1.setVisible(true);
        product1.setStatus(ProductStatus.APPROVED);
        product1.setPriceId(UUID.randomUUID());
        product1.setCategoryId(UUID.randomUUID());
        product1.setEventTypesIds(new ArrayList<>());

        GetProductDTO product2 = new GetProductDTO();
        product2.setId(UUID.randomUUID());
        product2.setName("Regular Ticket");
        product2.setDescription("Regular Ticket for Sports Event");
        product2.setAvailable(false);
        product2.setVisible(true);
        product2.setStatus(ProductStatus.PENDING);
        product2.setPriceId(UUID.randomUUID());
        product2.setCategoryId(UUID.randomUUID());
        product2.setEventTypesIds(new ArrayList<>());

        products.add(product1);
        products.add(product2);

        // Filter logic
        Collection<GetProductDTO> filteredProducts = products.stream()
                .filter(product -> name == null || product.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(product -> categoryId == null || product.getCategoryId().equals(categoryId))
                .filter(product -> eventTypeId == null || product.getEventTypesIds().contains(eventTypeId))
                .filter(product -> minPrice == null || product.getPriceId() != null) //???????????????/
                .filter(product -> maxPrice == null || product.getPriceId() != null)
                .filter(product -> isAvailable == null || product.isAvailable() == isAvailable)
                .filter(product -> status == null || product.getStatus() == status)
                .filter(product -> description == null || product.getDescription().toLowerCase().contains(description.toLowerCase()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(filteredProducts, HttpStatus.OK);
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

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatedProductDTO> updateProduct(@PathVariable UUID id, @RequestBody UpdateProductDTO product) {
        // Temporarily faking the update process, should call a get by id
        UpdatedProductDTO updatedProduct = new UpdatedProductDTO();

        updatedProduct.setId(UUID.randomUUID());
        updatedProduct.setName(product.getName());
        updatedProduct.setDescription(product.getDescription());
        updatedProduct.setPictures(product.getPictures());
        updatedProduct.setAvailable(product.isAvailable());
        updatedProduct.setVisible(product.isVisible());
        updatedProduct.setPriceId(product.getPriceId());
        updatedProduct.setEventTypesIds(product.getEventTypesIds());
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

        return new ResponseEntity<>("Product with ID " + id + " deleted successfully.", HttpStatus.NO_CONTENT);
    }

}
