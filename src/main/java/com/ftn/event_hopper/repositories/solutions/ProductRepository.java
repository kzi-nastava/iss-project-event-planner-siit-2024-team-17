package com.ftn.event_hopper.repositories.solutions;

import com.ftn.event_hopper.models.solutions.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
}
