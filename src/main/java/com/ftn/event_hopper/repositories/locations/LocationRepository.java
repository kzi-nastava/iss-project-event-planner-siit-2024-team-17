package com.ftn.event_hopper.repositories.locations;

import com.ftn.event_hopper.models.locations.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
}
