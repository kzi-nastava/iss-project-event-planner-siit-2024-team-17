package com.ftn.event_hopper.repositories.eventTypes;

import com.ftn.event_hopper.models.eventTypes.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventTypeRepository extends JpaRepository<EventType, UUID> {

}
