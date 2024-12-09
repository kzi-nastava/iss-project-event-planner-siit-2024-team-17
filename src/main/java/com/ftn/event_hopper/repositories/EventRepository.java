package com.ftn.event_hopper.repositories;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findTop5ByLocationIdAndPrivacyOrderByMaxAttendanceDesc(UUID locationId, EventPrivacyType privacyType);

}
