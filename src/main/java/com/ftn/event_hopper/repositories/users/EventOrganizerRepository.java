package com.ftn.event_hopper.repositories.users;

import com.ftn.event_hopper.models.users.EventOrganizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface EventOrganizerRepository extends JpaRepository<EventOrganizer, UUID> {
}
