package com.ftn.event_hopper.repositories.solutions;

import com.ftn.event_hopper.models.solutions.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p FROM Product p WHERE p IN :products")
    List<Product> findTop5ByProductsInSetOrderByName(@Param("products") Set<Product> products);

}
