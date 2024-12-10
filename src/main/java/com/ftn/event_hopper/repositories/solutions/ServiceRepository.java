package com.ftn.event_hopper.repositories.solutions;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.solutions.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<Service, UUID>, JpaSpecificationExecutor<Service> {
    @Query("SELECT s.id FROM Service s")
    List<UUID> findServiceIds();
}
