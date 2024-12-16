package com.ftn.event_hopper.repositories.solutions;

import com.ftn.event_hopper.models.solutions.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> , JpaSpecificationExecutor<Product> {


    Page<Product> findAll(Specification<Product> specification, Pageable page);

    @Query("SELECT p.id FROM Product p ")
    List<UUID> findProductIds();

    public Product findByCategory_Id(UUID categoryId);

    Page<Product> findAll(Pageable page);

    List<Product> findByIsDeletedFalse();
}
