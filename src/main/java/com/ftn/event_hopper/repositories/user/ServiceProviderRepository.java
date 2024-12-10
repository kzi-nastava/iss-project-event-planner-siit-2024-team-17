package com.ftn.event_hopper.repositories.user;


import com.ftn.event_hopper.models.solutions.Product;
import com.ftn.event_hopper.models.users.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, UUID> {


    List<ServiceProvider> findByCompanyLocationCity(String city);
}
