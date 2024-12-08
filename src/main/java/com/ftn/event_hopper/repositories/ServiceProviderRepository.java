package com.ftn.event_hopper.repositories;


import com.ftn.event_hopper.models.users.ServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, UUID> {
}
