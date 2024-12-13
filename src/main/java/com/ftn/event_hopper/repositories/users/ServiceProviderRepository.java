package com.ftn.event_hopper.repositories.users;


import com.ftn.event_hopper.models.users.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, UUID> {


    List<ServiceProvider> findByCompanyLocationCity(String city);
}
