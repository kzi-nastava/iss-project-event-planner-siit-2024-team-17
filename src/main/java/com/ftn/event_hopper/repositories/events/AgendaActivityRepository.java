package com.ftn.event_hopper.repositories.events;

import com.ftn.event_hopper.models.events.AgendaActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AgendaActivityRepository  extends JpaRepository<AgendaActivity, UUID> {
}
