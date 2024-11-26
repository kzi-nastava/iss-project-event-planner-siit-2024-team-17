package com.ftn.event_hopper.controllers.solutions;

import com.ftn.event_hopper.dtos.solutions.GetProductDTO;
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
public class ProductController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GetProductDTO>> getProducts() {
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

}
