package com.ftn.event_hopper.repositories;

import com.ftn.event_hopper.models.events.Event;
import com.ftn.event_hopper.models.shared.EventPrivacyType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    List<Event> findTop5ByLocationCityAndPrivacyAndTimeAfterOrderByMaxAttendanceDesc(String city, EventPrivacyType privacyType, LocalDateTime now);

    Page<Event> findAll(Pageable page);

}
