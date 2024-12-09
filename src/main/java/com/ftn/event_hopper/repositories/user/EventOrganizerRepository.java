package com.ftn.event_hopper.repositories.user;

import com.ftn.event_hopper.models.users.EventOrganizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventOrganizerRepository extends JpaRepository<EventOrganizer, UUID> {
}
