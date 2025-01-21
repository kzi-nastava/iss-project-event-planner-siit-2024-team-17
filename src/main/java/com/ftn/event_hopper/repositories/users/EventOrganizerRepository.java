package com.ftn.event_hopper.repositories.users;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.users.EventOrganizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EventOrganizerRepository extends JpaRepository<EventOrganizer, UUID> {
    Optional<EventOrganizer> findByEventsContaining(Event event);
}
