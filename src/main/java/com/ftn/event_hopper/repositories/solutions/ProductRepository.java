package com.ftn.event_hopper.repositories.solutions;

import com.ftn.event_hopper.models.solutions.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {


    public Product findByCategory_Id(UUID categoryId);

    Page<Product> findAll(Pageable page);

}
