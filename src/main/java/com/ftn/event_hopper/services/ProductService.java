package com.ftn.event_hopper.services;

import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.repositories.PersonRepository;
import com.ftn.event_hopper.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product findOne(UUID id) {
        return productRepository.findById(id).orElseGet(null);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

}
